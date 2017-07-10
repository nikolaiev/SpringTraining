package ua.rd;

import ua.rd.domain.Tweet;
import ua.rd.domain.repository.InMemTweetRepository;
import ua.rd.domain.repository.TweetRepository;
import ua.rd.domain.service.SimpleTweetService;
import ua.rd.domain.service.TweetService;
import ua.rd.ioc.ApplicationContext;
import ua.rd.ioc.Config;
import ua.rd.ioc.Context;
import ua.rd.ioc.JavaConfig;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class IoCRunner {
    public static void main(String[] args) {


        Map<String,Map<String,Object>> beanDescriptions=new HashMap<String, Map<String,Object>>(){
            /*initializing block*/
            {
                put("tweetRepository", new HashMap<String,Object>(){
                    {
                        put("class",InMemTweetRepository.class);
                        put("proto",false);
                    }
                });

                put("tweetService", new HashMap<String,Object>(){
                    {
                        put("class",SimpleTweetService.class);
                        put("proto",true);
                    }
                });

                put("tweet2", new HashMap<String,Object>(){
                    {
                        put("class",Tweet.class);
                        put("proto",true);
                    }
                });

            }
        };

        Config config=new JavaConfig(beanDescriptions);
        Context context=new ApplicationContext(config);

        System.out.println(Arrays.toString(context.getBeanDefinitionsName()));

        TweetRepository tweetRepository=context.getBean("tweetRepository");

        Tweet tweet=context.getBean("tweet2");

        TweetService service=context.getBean("tweetService");

        System.out.println(service.getAllTweets());

        System.out.println(tweetRepository.getAllTweets());
        System.out.println(tweetRepository.getClass());

        System.out.println(tweet.getMsg());
        System.out.println(tweet.getUser());
    }
}
