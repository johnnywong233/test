package saas.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.util.UrlPathHelper;
import saas.annotation.RootResource;
import saas.annotation.TenantResource;

/**
 * Author: Johnny
 * Date: 2017/8/17
 * Time: 0:14
 */
public class TenantResolveInterceptor implements HandlerInterceptor {

    static final String TENANT_BUSINESS_NAME_KEY = "businessName";

    private final UrlPathHelper urlPathHelper = new UrlPathHelper();

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        String businessName = resolve(urlPathHelper.getServletPath(request));
        request.setAttribute(TENANT_BUSINESS_NAME_KEY, businessName);

        // restrict the access
        HandlerMethod method = (HandlerMethod) handler;
        TenantResource tenantResource = method.getMethodAnnotation(TenantResource.class);
        RootResource rootResource = method.getMethodAnnotation(RootResource.class);

        boolean isRootResource = false;

        // get annotation from class when no annotation is specified
        if (tenantResource == null && rootResource == null) {
            tenantResource = AnnotationUtils.findAnnotation(method.getBeanType(), TenantResource.class);
            rootResource = AnnotationUtils.findAnnotation(method.getBeanType(), RootResource.class);
        }

        // still with no annotation, set default
        if (tenantResource == null && rootResource == null) {
            isRootResource = true;
        }

        // tenant resource
        if (tenantResource != null && !StringUtils.hasText(businessName)) {
            throw new NoHandlerFoundException(request.getMethod(), request.getRequestURI(), null);
        }

        // root resource
        if ((rootResource != null || isRootResource) && StringUtils.hasText(businessName)) {
            throw new NoHandlerFoundException(request.getMethod(), request.getRequestURI(), null);
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    /**
     * resolve to the actual business name
     */
    private String resolve(String servletPath) {
        if (!servletPath.isEmpty()) {
            return servletPath.substring(1);
        }
        return "";
    }

}
