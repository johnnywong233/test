package aop.service.sub;

import aop.service.ProductService;
import org.springframework.stereotype.Component;

/**
 * Author: Johnny
 * Date: 2017/10/7
 * Time: 14:27
 */
@Component
public class SubService extends ProductService {
    public void demo() {
        System.out.println("execute sub service method");
    }
}
