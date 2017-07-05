package ua.rd.ioc;


public class SimpleBeanDefinition  implements BeanDefinition{
    private final String beanName;
    private final Class<?> beanClass;
    private boolean isPrototype=false;

    public SimpleBeanDefinition(String key, Class<?> value) {
        this(key,value,false);
    }

    public SimpleBeanDefinition(String key, Class<?> value,boolean isPrototype) {
        this.beanName=key;
        this.beanClass=value;
        this.isPrototype=false;
    }


    @Override
    public String getBeanName() {
        return beanName;
    }

    @Override
    //<T> Class<T>
    public Class<?> getBeanType() {
        return beanClass;
    }

    @Override
    public boolean isPrototype() {
        return isPrototype;
    }
}
