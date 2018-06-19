package retry.spring;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Author: Johnny
 * Date: 2018/6/19
 * Time: 22:14
 */
public class AnnotationAwareRetryOperationsInterceptor implements MethodInterceptor {
    //记录重试次数
    private int times = 0;

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        //获取拦截的方法中的Retryable注解
        Retryable retryable = method.getAnnotation(Retryable.class);
        if (retryable == null) {
            return proxy.invokeSuper(obj,args);
        } else { //有Retryable注解，加入异常重试逻辑
            int maxAttemps = retryable.maxAttemps();
            try {
                return proxy.invokeSuper(obj,args);
            } catch (Throwable e) {
                if(times++ == maxAttemps){
                    System.out.println("已达最大重试次数：" + maxAttemps + ",不再重试！");
                }else{
                    System.out.println("调用" + method.getName() + "方法异常，开始第" + times +"次重试。。。");
                    //注意这里不是invokeSuper方法，invokeSuper会退出当前interceptor的处理
                    proxy.invoke(obj,args);
                }
            }
        }
        return null;
    }
}
