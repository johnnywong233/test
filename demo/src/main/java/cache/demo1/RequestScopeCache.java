package cache.demo1;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Johnny
 * Date: 2017/9/3
 * Time: 16:50
 */
@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "request")
public class RequestScopeCache {
    static final Object NONE = new Object();

    private final Map<InvocationContext, Object> cache = new HashMap<>();

    @SuppressWarnings("unchecked")
    public Object get(InvocationContext invocationContext) {
        return cache.getOrDefault(invocationContext, NONE);
    }

    @SuppressWarnings("unchecked")
    public void put(InvocationContext methodInvocation, Object result) {
        cache.put(methodInvocation, result);
    }
}
