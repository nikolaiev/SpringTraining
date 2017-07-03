package ua.rd.ioc;

import ua.rd.domain.Tweet;

public interface Context {

    String [] getBeanDefinitionsName();

    <T> T getBean(String tweet);
}
