package ua.rd.ioc;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
public class JavaConfig implements Config{

    private final BeanDefinition[] beanDefinitions;

    public JavaConfig(){
        beanDefinitions=new BeanDefinition[]{};
    }

    public JavaConfig(Map<String, Map<String, Object>> beanDescriptions) {
        beanDefinitions=beanDescriptions.entrySet().stream()
                .map(e->
                        new SimpleBeanDefinition(e.getKey(),
                                (Class<?>) e.getValue().get("class"),
                                (Boolean) e.getValue().get("proto")))
                .toArray(BeanDefinition[]::new);
    }

    public BeanDefinition[] getBeanDefinitions() {
        return beanDefinitions;
    }
}
