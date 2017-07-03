package ua.rd.ioc;

import ua.rd.domain.Tweet;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Student on 7/3/2017.
 */
public class IoCRunner {
    public static void main(String[] args) {
        Map<String,Class<?>> beanDescriptions=new HashMap<String, Class<?>>(){
            /*initializing block*/
            {
                put("tweet", Tweet.class);
            }
        };

        Config config=new JavaConfig(beanDescriptions);
        Context context=new ApplicationContext(config);

        System.out.println(Arrays.toString(context.getBeanDefinitionsName()));

        Tweet tweet=context.getBean("tweet");

        System.out.println(tweet.toString());
    }
}
