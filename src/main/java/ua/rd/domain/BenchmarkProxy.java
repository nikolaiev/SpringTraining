package ua.rd.domain;

import ua.rd.domain.repository.Benchmark;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.time.Instant;
import java.util.Arrays;

public class BenchmarkProxy {

    private final Class<?> type;

    private final Object bean;

    public BenchmarkProxy(Class<?> type, Object bean) {
        this.type = type;
        this.bean = bean;
    }

    public Object benchmarkProxy() {
        Class<?> beanType=bean.getClass();

        boolean isAnnotatedBenchmark= Arrays.stream(beanType.getMethods()).anyMatch(method ->
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

        return Proxy.newProxyInstance(bean.getClass().getClassLoader(),interfaces
                ,handler);
    }
}
