package ua.rd.ioc;


public class SimpleBeanDefinition  implements BeanDefinition{
    private final String beanName;
    private final Class<?> beanClass;

    public SimpleBeanDefinition(String key, Class<?> value) {
        this.beanName=key;
        this.beanClass=value;
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
}
