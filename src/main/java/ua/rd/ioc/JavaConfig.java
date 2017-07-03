package ua.rd.ioc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class JavaConfig implements Config{

    private final BeanDefinition[] beanDefinitions;

    //TODO fix
    public JavaConfig(){
        beanDefinitions=new BeanDefinition[]{};
    }

    public JavaConfig(Map<String, Class<?>> beanDescriptions) {
        beanDefinitions=beanDescriptions.entrySet().stream()
                .map(e->new SimpleBeanDefinition(e.getKey(),e.getValue()))
                .toArray(BeanDefinition[]::new);
    }

    public BeanDefinition[] getBeanDefinitions() {
        return beanDefinitions;
    }
}
