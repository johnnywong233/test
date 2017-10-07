package aop.service;

import aop.annotation.NeedSecured;
import aop.annotation.NeedSecuredClass;
import aop.annotation.NeedSecuredSource;
import aop.log.Loggable;
import org.springframework.stereotype.Component;

/**
 * Author: Johnny
 * Date: 2017/10/7
 * Time: 14:28
 */
@Component
@NeedSecured
@NeedSecuredSource
@NeedSecuredClass
public class ProductService implements Loggable {
    public String getName() {
        System.out.println("execute get name");
        return "product service";
    }

    public void exDemo() throws IllegalAccessException {
        System.out.println("execute ex demo");
        throw new IllegalAccessException("TEST");
    }

    public void findById(Long id) {
        System.out.println("execute find by id");
    }

    public void findByTwoArgs(Long id, String name) {
        System.out.println("execute find by id and name");
    }

    @Override
    public void log() {
        System.out.println("log from product service");
    }
}
