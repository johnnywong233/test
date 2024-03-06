package retry.guava;

import com.github.rholder.retry.RetryException;
import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.github.rholder.retry.WaitStrategies;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Author: Johnny
 * Date: 2018/6/19
 * Time: 22:00
 */
@Slf4j
public class RetryTest {
    public static void main(String[] args) {
        Retryer<Boolean> retry = RetryerBuilder.<Boolean>newBuilder()
                .retryIfResult(Objects::isNull)// 设置自定义段元重试源
                .retryIfExceptionOfType(Exception.class)// 设置异常重试源
                .retryIfRuntimeException()// 设置异常重试源
                .withStopStrategy(StopStrategies.stopAfterAttempt(5))// 设置重试5次，同样可以设置重试超时时间
                .withWaitStrategy(WaitStrategies.fixedWait(5L, TimeUnit.SECONDS))// 设置每次重试间隔，5秒
                .build();
        try {
            retry.call(new Callable<>() {
                int i = 0;

                @Override
                public Boolean call() throws Exception {
                    i++;
                    log.info("第{}次执行！", i);
                    // do something
                    if (i < 6) {// 模拟错2次
                        log.info("模拟执行失败！");
                        throw new IOException("异常");
                    }
                    log.info("模拟执行成功！");
                    return true;
                }
            });
        } catch (RetryException e) {
            log.info("超过重试次数", e);
        } catch (ExecutionException e) {
            log.info("重试框架异常", e);
        }
    }

}
