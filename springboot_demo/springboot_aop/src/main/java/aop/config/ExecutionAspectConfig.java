package aop.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Author: Johnny
 * Date: 2017/10/7
 * Time: 14:44
 * <p>
 * 5种Advice示例:
 * <p>
 * @Before("matchAnno()") <p>
 * @After("matchAnno())") <p>
 * @AfterReturning("matchException()") <p>
 * @AfterThrowing("matchException()") <p>
 * @Around("matchException()") <p>
 *
 * @Before(value="matchLongArg()&&args(productId)")
 * public void beforeWithArgs(Long productId)
 * <p>
 * @AfterReturning(value="matchReturn()",returning="returnValue")
 * public void getResult(Object returnValue)
 * <p>
 * 匹配AOP对象的目标对象为指定类型的方法,即LogService的aop代理对象的方法
 * @Pointcut("this(aop.log.Loggable)") 匹配实现Loggable接口的目标对象(而不是aop代理后的对象)的方法
 * @Pointcut("target(aop.log.Loggable)") this 可以拦截 DeclareParents(Introduction)
 * target 不拦截 DeclareParents(Introduction)
 * <p>
 * 匹配所有以Service结尾的bean里头的方法
 * @Pointcut("bean(*Service)")
 * <p>
 * 匹配任何以find开头而且只有一个Long参数的方法
 * @Pointcut("execution(* *..find*(Long))")
 * <p>
 * 匹配任何以find开头的而且第一个参数为Long型的方法
 * @Pointcut("execution(* *..find*(Long, ..))")
 * <p>
 * 匹配任何只有一个Long参数的方法
 * @Pointcut("within(aop..*) && args(Long)")
 * <p>
 * 匹配第一个参数为Long型的方法
 * @Pointcut("within(aop..*) && args(Long, ..)")
 * <p>
 * 匹配包下面的任何公共方法
 * @Pointcut("execution(public * aop.service.*.*(..))")
 * <p>
 * 匹配aop包及子包下Service类中无参方法
 * @Pointcut("execution(* aop..*Service.*())")
 * <p>
 * 匹配aop包及子包下Service类中的任何只有一个参数的方法
 * @Pointcut("execution(* aop..*Service.*(*))")
 * <p>
 * 匹配aop包及子包下任何类的任何方法
 * @Pointcut("execution(* aop..*.*(..))")
 * <p>
 * 匹配aop包及子包下返回值为String的任何方法
 * @Pointcut("execution(String aop..*.*(..))")
 * <p>
 * 匹配抛出IllegalAccessException异常
 * execution(public * aop.service.*.*(..) throws java.lang.IllegalAccessException)
 * <p>
 * 匹配ProductService类里头的所有方法
 * @Pointcut("within(aop.service.ProductService)")
 * <p>
 * 匹配aop包及子包下所有类的方法
 * @Pointcut("within(aop..*)")
 * <p>
 * 匹配方法标注有AdminOnly的注解的方法
 * @Pointcut("@annotation(aop.anno.AdminOnly) && within(aop..*)")
 * <p>
 * 匹配标注有NeedSecured的类底下的方法 class级别
 * @Pointcut("@within(aop.anno.NeedSecured) && within(aop..*)")
 * <p>
 * 匹配标注有NeedSecured的类及其子类的方法 runtime级别
 * 在spring context的环境下,二者没有区别
 * @Pointcut("@target(aop.anno.NeedSecured) && within(aop..*)")
 * <p>
 * 匹配传入的参数类标注有Repository注解的方法
 * @Pointcut("@args(aop.anno.NeedSecured) && within(aop..*)")
 */
@Aspect
@Component
public class ExecutionAspectConfig {
    @Pointcut("execution(public * aop.service..*Service.*(..) throws java.lang.IllegalAccessException)")
    public void matchCondition() {
        System.out.println("###match condition");
    }

    @Pointcut("@annotation(aop.annotation.AdminOnly) && within(aop..*)")
    public void matchAnnotation() {
        System.out.println("###match annotation ");
    }

    @Pointcut("execution(* *..find*(Long)) && within(aop..*) ")
    public void matchLongArg() {
        System.out.println("###match args Long");
    }

    @Pointcut("execution(public * aop.service..*Service.*(..) throws java.lang.IllegalAccessException) && within(aop..*)")
    public void matchException() {
        System.out.println("###match exception");
    }

    @Pointcut("execution(String aop..*.*(..)) && within(aop..*)")
    public void matchReturn() {
        System.out.println("### match return");
    }

    @Pointcut("bean(logService)")
    public void matchCondition1() {
        System.out.println("### match condition 1");
    }

    @Pointcut("within(aop.service.sub.*)")
    public void matchType() {
        System.out.println("### match type");
    }

    @Before("matchCondition()")
    public void before2() {
        System.out.println("###before method 2");
    }

    @Before("matchLongArg() && args(productId)")
    public void beforeWithArgs(Long productId) {
        System.out.println("###before, get args:" + productId);
    }

    @Around("matchException()")
    public java.lang.Object after(ProceedingJoinPoint joinPoint) {
        System.out.println("###before");
        java.lang.Object result = null;
        try {
            result = joinPoint.proceed(joinPoint.getArgs());
            System.out.println("###after returning");
        } catch (Throwable e) {
            System.out.println("###exception");
            //ignore throw in test code
            e.printStackTrace();
        } finally {
            System.out.println("###finally");
        }
        return result;
    }

    @Before("matchType()")
    public void before1() {
        System.out.println("###before method 1");
    }

    @Before("matchCondition()")
    public void before() {
        System.out.println("###before method ");
    }

    @After("matchCondition1()")
    public void after(){
        System.out.println("###after method");
    }

    @AfterReturning("matchReturn()")
    public void afterReturning(){
        System.out.println("###afterReturning method");
    }

    @AfterThrowing("matchAnnotation()")
    public void afterThrowing(){
        System.out.println("###afterThrowing method.");
    }
}