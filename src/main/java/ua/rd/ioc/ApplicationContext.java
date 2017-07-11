package ua.rd.ioc;

import ua.rd.domain.repository.Benchmark;

import java.lang.reflect.*;
import java.time.Instant;
import java.util.*;


public class ApplicationContext implements   Context {
    private Config config;

    private final Map<String,Object> context=new HashMap<>();

    public ApplicationContext(){
        this(null);
    }

    public ApplicationContext(Config config) {
        this.config=config;

        if(config!=null)
            initApplicationContext();
    }

    private void initApplicationContext() {
        BeanDefinition [] beanDefinitions=config.getBeanDefinitions();

        for(BeanDefinition bd:beanDefinitions){
            String beanName=bd.getBeanName();
            createBean(beanName);
        }
    }

    private void createBean(String beanName) {

        if(checkBeanIsProto(beanName)||context.containsKey(beanName)){
           return;
        }

        Object bean=createProtoBean(beanName);
         context.put(beanName, bean);
    }

    private boolean checkBeanIsProto(String beanName){
        BeanDefinition bd=getBeanDefinition(beanName);
        return bd.isPrototype();
    }

    private Object createProtoBean(String beanName){
        BeanDefinition bd=getBeanDefinition(beanName);

        Class<?> beanType=bd.getBeanType();
        final Constructor<?> beanConstructor = beanType.getConstructors()[0];

        Object bean;

        if(beanConstructor.getParameterCount()==0) {
            bean = createBeanWithDefaultConstructor(beanType);
        }
        else{
            bean = createBeanWithNoDefaultConstructor(beanType);
        }

        callInitMethod(bean);
        return createBenchmarkProxy(bean);
    }

    private <T> T createBenchmarkProxy(T bean) {
        Class<?> beanType=bean.getClass();

        boolean isAnnotatedBenchmark=Arrays.stream(beanType.getMethods()).anyMatch(method ->
                method.isAnnotationPresent(Benchmark.class) && method.getAnnotation(Benchmark.class).value()
        );

        if(!isAnnotatedBenchmark)
            return bean;

        InvocationHandler handler= (proxy, method, args) -> {

            Object res=null;

            Method beanMethod=bean.getClass().getMethod(method.getName(),method.getParameterTypes());

            if(beanMethod.isAnnotationPresent(Benchmark.class) && beanMethod.getAnnotation(Benchmark.class).value()) {
                long start = Instant.now().toEpochMilli();

                System.out.println("Benchmarking "+method.getName());

                res=method.invoke(bean,args);

                long end = Instant.now().toEpochMilli();

                System.out.println("time is "+ (end-start));
            }
            else {
                res=method.invoke(bean,args);
            }


            return res;

        };

        Class<?>[] interfaces = bean.getClass().getInterfaces();

        return (T) Proxy.newProxyInstance(bean.getClass().getClassLoader(),interfaces
                ,handler);

    }

    private BeanDefinition getBeanDefinition(String beanName) {
        return Arrays.stream(config.getBeanDefinitions())
                .filter(bd -> bd.getBeanName().equals(beanName))
                .findAny().orElseThrow(()->new RuntimeException("Bean not found with name "+beanName));
    }

    public String[] getBeanDefinitionsName(){
        if(config==null){
            return new String[]{};
        }

        return Arrays.stream(config.getBeanDefinitions())
                .map(BeanDefinition::getBeanName).toArray(String[]::new);

    }

    @Override
    public <T> T getBean(String beanName)  {
        if(checkBeanIsProto(beanName))
            return (T)createProtoBean(beanName);

        T bean=(T)context.get(beanName);
        return bean;
    }

    private <T> void callInitMethod(T bean) {
        Class<?> beanClass = bean.getClass();

        try {

            Method initMethod = beanClass.getMethod("init");

            try {
                initMethod.invoke(bean);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (NoSuchMethodException e) {
        }
    }

    private <T> T  createBeanWithDefaultConstructor(Class<T> beanType) {
        return newInstance(beanType);
    }

    private <T> T createBeanWithNoDefaultConstructor(Class<T> beanType) {

        BeanDefinition [] beanDefinitions=config.getBeanDefinitions();

        final Constructor<?> beanConstructor = beanType.getConstructors()[0];
        Class [] paramTypes=beanConstructor.getParameterTypes();
        Object[] paramValues=new Object[paramTypes.length];

        int i=0;

        for(Class<?> subBeanType : paramTypes){

            String beanName=null;

            for(BeanDefinition bd :beanDefinitions){
                Class<?>[] interfaces = bd.getBeanType().getInterfaces();

                if(Arrays.stream(interfaces).anyMatch(e->e.equals(subBeanType)))
                {
                    beanName=bd.getBeanName();
                    break;
                }
            }
            T subBean=null;

            if(beanName!=null) {
                subBean = getBean(beanName);
            }

            paramValues[i++]=subBean;
        }

        return newInstance(beanType,paramTypes,paramValues);
    }

    private <T> T newInstance(Class<T> beanType, Class[] paramTypes, Object[] vals){
        try {
            Constructor<?> constructor=beanType.getConstructor(paramTypes);
            return (T) constructor.newInstance(vals);

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
    private <T> T newInstance(Class<T> cl){
        try {
            return cl.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
