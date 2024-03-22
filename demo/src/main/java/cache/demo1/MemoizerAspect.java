package cache.demo1;

import jakarta.annotation.Resource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Author: Johnny
 * Date: 2017/9/3
 * Time: 16:48
 * MemoizerAspect - Function call memoization utility.
 */
@Aspect
public class MemoizerAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(MemoizerAspect.class);

    @Resource
    private RequestScopeCache requestScopeCache;

    /**
     * Memoize current call
     *
     * @param pjp joint point
     * @return result
     * @throws Throwable call failure
     */
    @Around("@annotation(cache.demo1.Memoize)")
    public Object memoize(ProceedingJoinPoint pjp) throws Throwable {
        InvocationContext invocationContext = new InvocationContext(
                pjp.getSignature().getDeclaringType(),
                pjp.getSignature().getName(),
                pjp.getArgs()
        );
        Object result = requestScopeCache.get(invocationContext);
        if (RequestScopeCache.NONE == result) {
            result = pjp.proceed();
            LOGGER.info("Memorizing result {}, for method invocation: {}", result, invocationContext);
            requestScopeCache.put(invocationContext, result);
        } else {
            LOGGER.info("Using memorized result: {}, for method invocation: {}", result, invocationContext);
        }
        return result;
    }
}
