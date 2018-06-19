package retry.guava;

import com.github.rholder.retry.RetryException;
import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.github.rholder.retry.WaitStrategies;
import com.google.common.base.Predicates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Author: Johnny
 * Date: 2018/6/19
 * Time: 22:00
 */
public class RetryTest {
    private static final Logger logger = LoggerFactory.getLogger(RetryTest.class);
    public static void main(String[] args){
        Retryer<Boolean> retryer = RetryerBuilder.<Boolean>newBuilder()
                .retryIfResult(Predicates.isNull())// 设置自定义段元重试源
                .retryIfExceptionOfType(Exception.class)// 设置异常重试源
                .retryIfRuntimeException()// 设置异常重试源
                .withStopStrategy(StopStrategies.stopAfterAttempt(5))// 设置重试5次，同样可以设置重试超时时间
                .withWaitStrategy(WaitStrategies.fixedWait(5L, TimeUnit.SECONDS))// 设置每次重试间隔，5秒
                .build();
        try {
            retryer.call(new Callable<Boolean>() {
                int i = 0;
                @Override
                public Boolean call() throws Exception {
                    i++;
                    logger.info("第{}次执行！", i);
                    // do something
                    if (i<6) {// 模拟错2次
                        logger.info("模拟执行失败！");
                        throw new IOException("异常");
                    }
                    logger.info("模拟执行成功！");
                    return true;
                }
            });
        } catch (RetryException e) {
            logger.info("超过重试次数", e);
        } catch (ExecutionException e) {
            logger.info("重试框架异常", e);
        }
    }

}
