package retry.spring;

import org.springframework.cglib.proxy.Enhancer;

/**
 * Author: Johnny
 * Date: 2018/6/19
 * Time: 22:13
 */
public class SpringRetryProxy {
    Object newProxyInstance(Object target) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(new AnnotationAwareRetryOperationsInterceptor());
        return enhancer.create();
    }
}
