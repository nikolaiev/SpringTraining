package ua.rd.ioc;

import org.junit.Test;
import ua.rd.domain.Tweet;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ApplicationContextTest {


    public ApplicationContextTest(){
        //must be empty
    }

    Map<String,Class<?>> createTestBeanDescription(){
        Map<String,Class<?>> beanDescriptions=new HashMap<String, Class<?>>(){
            /*initializing block*/
            {
                put("tweet", Tweet.class);
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

        assertEquals(1,beanDefinition.length);
    }



    @Test
    public void testBeanDefinitionNamesWithJavaConfig(){
        Map<String, Class<?>> testBeanDescription = createTestBeanDescription();
        Config javaConfig =new JavaConfig(testBeanDescription );
        Context context=new ApplicationContext(javaConfig);

        String [] beanDefinitionName= context.getBeanDefinitionsName();

        assertTrue(Arrays.asList(beanDefinitionName).contains("tweet"));
    }

    @Test
    public void getBean() throws Exception {
        Map<String, Class<?>> testBeanDescription = createTestBeanDescription();
        Config javaConfig =new JavaConfig(testBeanDescription );
        Context context=new ApplicationContext(javaConfig);

        assertNotNull(context.getBean("tweet"));
    }
}