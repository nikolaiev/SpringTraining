package ua.rd.ioc;

import ua.rd.domain.Tweet;
import ua.rd.domain.repository.Benchmark;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


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
        //TODO check if prototype
        if(context.containsKey(beanName)){
           return;
        }

        //TODO check if bean has args or not args constructor
        BeanDefinition bd=getBeanDefinition(beanName);

        Class<?> beanType=bd.getBeanType();
        final Constructor<?> beanConstructor = beanType.getConstructors()[0];

        Object bean;

        if(beanConstructor.getParameterCount()==0) {
            bean = createBeanWithNoDefaultConstructor(beanType);
        }
        else{
            bean = createBeanWithDefaultConstructor(beanType);
        }

        callInitMethod(bean);
        bean=createBenchmarkProxy(bean);

        //TODO if (bean is prototype) do not put
        context.put(beanName, bean);
    }

    private Object createBenchmarkProxy(Object bean) {
        //TODO check if method has annotation
        Class<?> beanType=bean.getClass();

        //TODO check if annotation ON/OFF
        boolean isAnnotatedBenchmark=Arrays.stream(beanType.getMethods()).anyMatch(method ->
            method.isAnnotationPresent(Benchmark.class)
        );

        if(!isAnnotatedBenchmark)
            return bean;

        InvocationHandler handler= (proxy, method, args) -> {
            //TODO check if method annotated
            System.out.printf("Benchmarking "+method.getName());
            return method.invoke(bean,args);

        };

        return Proxy.newProxyInstance(bean.getClass().getClassLoader(),
                bean.getClass().getInterfaces(),handler);

    }

    private BeanDefinition getBeanDefinition(String beanName) {
        return Arrays.stream(config.getBeanDefinitions())
                .filter(bd -> bd.getBeanName().equals(beanName))
                .findAny().orElseThrow(()->new RuntimeException("Bean not found with name "+beanName));
    }

    private <T> T  createBeanWithDefaultConstructor(Class<?> beanName) {
        //TODO implement
        return null;
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
        //TODO if(beanDefinition is prototype) createBean

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

    private <T> T createBeanWithNoDefaultConstructor(Class<T> beanType) {

        return newInstance(beanType);
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
