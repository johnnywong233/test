package cache.demo1;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Arrays;

/**
 * Author: Johnny
 * Date: 2017/9/3
 * Time: 16:49
 */
public class InvocationContext {
    private static final String TEMPLATE = "%s.%s(%s)";

    private final Class targetClass;
    private final String targetMethod;
    private final Object[] args;

    InvocationContext(Class targetClass, String targetMethod, Object[] args) {
        this.targetClass = targetClass;
        this.targetMethod = targetMethod;
        this.args = args;
    }

    public Class getTargetClass() {
        return targetClass;
    }

    public String getTargetMethod() {
        return targetMethod;
    }

    public Object[] getArgs() {
        return args;
    }

    @Override
    public boolean equals(Object that) {
        return EqualsBuilder.reflectionEquals(this, that);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(TEMPLATE, targetClass.getName(), targetMethod, Arrays.toString(args));
    }
}
