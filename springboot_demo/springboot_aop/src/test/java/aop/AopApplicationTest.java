package aop;

import aop.entity.Product;
import aop.log.LogService;
import aop.service.ProductService;
import aop.service.sub.SubService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Author: Johnny
 * Date: 2017/10/7
 * Time: 14:33
 */
@SpringBootTest
public class AopApplicationTest {
    @Autowired
    ProductService productService;

    @Autowired
    SubService subService;

    @Autowired
    LogService logService;

    @Test
    public void test() {
        System.out.println("###begin test###");
        productService.findById(1L);
        productService.findByTwoArgs(1L, "hello");
        productService.getName();
        subService.demo();
        try {
            productService.exDemo();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        logService.log();
        productService.log();
        logService.annoArg(new Product());
    }

}