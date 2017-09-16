package saas.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import saas.util.TenantContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Author: Johnny
 * Date: 2017/8/17
 * Time: 0:13
 */
public class RoutingDataSourceInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String businessName = (String) request.getAttribute(TenantResolveInterceptor.TENANT_BUSINESS_NAME_KEY);
        TenantContextHolder.setBusinessName(businessName);
        return true;
    }
}
