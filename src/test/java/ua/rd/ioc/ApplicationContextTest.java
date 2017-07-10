package ua.rd.ioc;

import org.junit.Test;
import ua.rd.domain.Tweet;
import ua.rd.domain.repository.InMemTweetRepository;
import ua.rd.domain.repository.TweetRepository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ApplicationContextTest {


    public ApplicationContextTest(){
        //must be empty
    }

    Map<String, Map<String, Object>> createTestBeanDescription(){
        Map<String,Map<String,Object>> beanDescriptions=new HashMap<String, Map<String,Object>>(){
            /*initializing block*/
            {
                put("tweetRepository", new HashMap<String,Object>(){
                    {
                        put("class",InMemTweetRepository.class);
                        put("proto",false);
                    }
                });

                put("tweet2", new HashMap<String,Object>(){
                    {
                        put("class",Tweet.class);
                        put("proto",true);
                    }
                });

                put("tweet", new HashMap<String,Object>(){
                    {
                        put("class",Tweet.class);
                        put("proto",true);
                    }
                });


            }
        };

        return beanDescriptions;
    }

    @Test
    public void getBeanDefinitionsNameWithoutJavaConfig() throws Exception {
        Context context=new ApplicationContext();

        String [] names=context.getBeanDefinitionsName();

        assertEquals(names.length,0);

    }

    @Test
    public void testInitContextWithoutJavaConfig(){
        Context context=new ApplicationContext();

        assertNotNull(context);
    }


    @Test
    public void testInitContextWithEmptyJavaConfig(){
        Config javaConfig =new JavaConfig();
        Context context=new ApplicationContext(javaConfig);

        assertNotNull(context);
    }

    @Test
    public void testBeanDefinitionWithEmptyJavaConfig(){
        Config javaConfig =new JavaConfig();
        BeanDefinition [] beanDefinition= javaConfig.getBeanDefinitions();

        assertEquals(beanDefinition.length,0);
    }

    @Test
    public void testBeanDefinitionWithJavaConfig(){
        Config javaConfig =new JavaConfig(createTestBeanDescription());
        BeanDefinition [] beanDefinition= javaConfig.getBeanDefinitions();

        assertEquals(3,beanDefinition.length);
    }



    @Test
    public void testBeanDefinitionNamesWithJavaConfig(){
        Map<String, Map<String, Object>> testBeanDescription = createTestBeanDescription();
        Config javaConfig =new JavaConfig(testBeanDescription );
        Context context=new ApplicationContext(javaConfig);

        String [] beanDefinitionName= context.getBeanDefinitionsName();

        assertTrue(Arrays.asList(beanDefinitionName).contains("tweet"));
    }

    @Test
    public void getBean() throws Exception {
        Map<String, Map<String, Object>> testBeanDescription = createTestBeanDescription();
        Config javaConfig =new JavaConfig(testBeanDescription );
        Context context=new ApplicationContext(javaConfig);

        assertNotNull(context.getBean("tweet"));
    }

    @Test
    public void testGetToSameBeansEqual(){
        Config javaConfig =new JavaConfig(createTestBeanDescription() );
        Context context=new ApplicationContext(javaConfig);

        Object bean1=context.getBean("tweet");
        Object bean2=context.getBean("tweet");

        assertSame(bean1,bean2);
    }

    @Test
    public void testGetPrototypeBeansNotEqual(){
        Map<String, Map<String, Object>> testBeanDescription = createTestBeanDescription();
        Config javaConfig =new JavaConfig(testBeanDescription );
        Context context=new ApplicationContext(javaConfig);

        Object bean1=context.getBean("tweet");
        Object bean2=context.getBean("tweet");

        assertSame(bean1,bean2);
    }

    @Test
    public void testInitMethodCall(){
        Map<String, Map<String, Object>> testBeanDescription = createTestBeanDescription();
        Config javaConfig =new JavaConfig(testBeanDescription );
        Context context=new ApplicationContext(javaConfig);

        TweetRepository bean1=context.getBean("tweetRepository");
        assertNotNull(bean1.getAllTweets());
    }
}