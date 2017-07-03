package ua.rd.ioc;

import ua.rd.domain.Tweet;
import java.util.Arrays;
import java.util.Optional;


public class ApplicationContext implements   Context {
    private Config config;

    public ApplicationContext(){

    }

    public ApplicationContext(Config config) {
        this.config=config;
    }

    public String[] getBeanDefinitionsName(){
        if(config==null){
            return new String[]{};
        }

        return Arrays.stream(config.getBeanDefinitions())
                .map(BeanDefinition::getBeanName).toArray(String[]::new);

    }

    @Override
    public <T> T getBean(String tweet) {
        Optional<BeanDefinition> bean = Arrays.stream(config.getBeanDefinitions())
                .filter(bd -> bd.getBeanName().equals(tweet))
                .findAny();

        return (T)bean
                .map(BeanDefinition::getBeanType)
                .map(this::newInstance)
                .orElse(null);
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
