package saas.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;
import saas.util.TenantContextHolder;

/**
 * Author: Johnny
 * Date: 2017/8/17
 * Time: 0:13
 */
public class RoutingDataSourceInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        String businessName = (String) request.getAttribute(TenantResolveInterceptor.TENANT_BUSINESS_NAME_KEY);
        TenantContextHolder.setBusinessName(businessName);
        return true;
    }
}
