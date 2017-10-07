package aop.log;

import aop.annotation.AdminOnly;
import aop.entity.Product;
import org.springframework.stereotype.Service;

/**
 * Author: Johnny
 * Date: 2017/10/7
 * Time: 14:29
 */
@Service
public class LogService implements Loggable {
    @Override
    @AdminOnly
    public void log() {
        System.out.println("log from LogService");
    }

    public void annoArg(Product product) {
        System.out.println("execute log service annoArg");
    }
}
