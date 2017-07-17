package ua.rd.domain;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

public class BeanA {
    private BeanB beanB;

    public BeanA() {
    }

    public void setBeanB(BeanB beanB) {
        this.beanB = beanB;
    }

    public BeanA(BeanB beanB) {
        this.beanB = beanB;
        System.out.println(beanB.getClass());
    }


}
