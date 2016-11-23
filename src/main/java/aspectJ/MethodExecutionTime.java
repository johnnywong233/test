package aspectJ;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.util.StopWatch;

/**
 * Author: Johnny
 * Date: 2016/11/23
 * Time: 20:33
 */
@Aspect
public class MethodExecutionTime {
    @Around("execution(* *.*(..)) && @annotation(aspectJ.MonitorMethod)")
    public Object profile(ProceedingJoinPoint pjp) throws Throwable {
        StopWatch sw = new StopWatch(getClass().getSimpleName());
        try {
            sw.start(pjp.getSignature().toShortString());
            return pjp.proceed();
        } finally {
            sw.stop();
            System.out.println(sw.prettyPrint());
        }
    }
}
