package saas.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * specify that the resource should be accessed by root
 * Author: Johnny
 * Date: 2017/8/17
 * Time: 0:09
 */
@Target({METHOD, TYPE})
@Retention(RUNTIME)
public @interface RootResource {
}
