package cache.demo1;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Arrays;

/**
 * Author: Johnny
 * Date: 2017/9/3
 * Time: 16:49
 */
@Getter
@AllArgsConstructor
public class InvocationContext {
    private static final String TEMPLATE = "%s.%s(%s)";

    private final Class<?> targetClass;
    private final String targetMethod;
    private final Object[] args;

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
