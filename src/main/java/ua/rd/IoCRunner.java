package ua.rd;

import ua.rd.domain.Tweet;
import ua.rd.domain.repository.InMemTweetRepository;
import ua.rd.domain.repository.TweetRepository;
import ua.rd.ioc.ApplicationContext;
import ua.rd.ioc.Config;
import ua.rd.ioc.Context;
import ua.rd.ioc.JavaConfig;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class IoCRunner {
    public static void main(String[] args) {
        Map<String,Class<?>> beanDescriptions=new HashMap<String, Class<?>>(){
            /*initializing block*/
            {
                put("tweetRepository", InMemTweetRepository.class);
                put("tweet2", Tweet.class);

            }
        };

        Config config=new JavaConfig(beanDescriptions);
        Context context=new ApplicationContext(config);

        System.out.println(Arrays.toString(context.getBeanDefinitionsName()));

        TweetRepository tweetRepository=context.getBean("tweetRepository");

        System.out.println(tweetRepository.getAllTweets());
        System.out.println(tweetRepository.getClass());
    }
}
