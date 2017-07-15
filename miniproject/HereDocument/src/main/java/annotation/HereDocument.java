package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: Johnny
 * Date: 2016/12/31
 * Time: 15:43
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.SOURCE)
public @interface HereDocument {
}
