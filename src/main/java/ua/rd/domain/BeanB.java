package ua.rd.domain;

import lombok.AllArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

public class BeanB implements ApplicationContextAware{
    private BeanA beanA;
    private ApplicationContext context;

    /*public void setBeanA(BeanA beanA) {
        this.beanA = beanA;
    }*/

    public BeanB() {

    }

  /*  public BeanB(BeanA beanA) {
        this.beanA = beanA;
        System.out.println(beanA.getClass());
    }*/

    public void init(){
        beanA= context.getBean("beanA",BeanA.class);
        //  beanA.setBeanB(this);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context=applicationContext;
    }
}
