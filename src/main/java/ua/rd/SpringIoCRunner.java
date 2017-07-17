package ua.rd;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.rd.domain.Tweet;
import ua.rd.domain.repository.TweetRepository;
import ua.rd.domain.service.TweetService;


import java.util.Arrays;

public class SpringIoCRunner {
    public static void main(String[] args) {

        ConfigurableApplicationContext rootContext=
                new ClassPathXmlApplicationContext("repoContext.xml");

        ConfigurableApplicationContext serviceContext= new ClassPathXmlApplicationContext(new String[]{
                "serviceContext.xml"
        },rootContext);

        BeanDefinition bd= rootContext.getBeanFactory().getBeanDefinition("tweetRepository");
        System.out.println(bd.toString());

        TweetRepository repository= (TweetRepository) rootContext.getBean("tweetRepository");

        TweetService service=(TweetService) rootContext.getBean("tweetService");

        TweetRepository serviceFromRepo=(TweetRepository) rootContext.getBean("tweetRepo");

        BeanDefinition tweetRepoDefinition = rootContext.getBeanFactory().getBeanDefinition("tweetRepo");

        /*System.out.println(serviceFromRepo.getAllTweets());
        System.out.println(serviceFromRepo);
        System.out.println(tweetRepoDefinition);

        System.out.println(Arrays.toString(rootContext.getBeanDefinitionNames()));*/

        System.out.println(service.getAllTweets());
        System.out.println(Arrays.toString(service.getClass().getDeclaredMethods()));

        Tweet t1=service.newTweet("Andr","Hi!");
        Tweet t2=service.newTweet("Vlad","Buy!");

        System.out.println(t1==t2);

        //System.out.println(serviceFromRepo);
        //System.out.println(serviceFromRepo.getAllTweets());

        //System.out.println(repository.getAllTweets());
        //System.out.println(Arrays.toString(rootContext.getBeanDefinitionNames()));

        //System.out.println(service.getAllTweets());

        //serviceContext.close();
        rootContext.close();
    }
}
