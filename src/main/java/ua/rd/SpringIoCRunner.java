package ua.rd;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.rd.domain.repository.TweetRepository;


import java.util.Arrays;

public class SpringIoCRunner {
    public static void main(String[] args) {

        ConfigurableApplicationContext applicationContext=
                new ClassPathXmlApplicationContext("repoContext.xml","serviceContext.xml");
        BeanDefinition bd= applicationContext.getBeanFactory().getBeanDefinition("tweetRepository");
        System.out.println(bd.toString());

        TweetRepository repository= (TweetRepository) applicationContext.getBean("tweetRepository");

        System.out.println(repository.getAllTweets());
        System.out.println(Arrays.toString(applicationContext.getBeanDefinitionNames()));
        applicationContext.close();
    }
}
