package saas.util;

import org.springframework.util.Assert;

/**
 * Author: Johnny
 * Date: 2017/8/17
 * Time: 0:07
 */
public class TenantContextHolder {
    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    public static void setBusinessName(String businessName) {
        Assert.notNull(businessName, "businessName cannot be null");
        CONTEXT_HOLDER.set(businessName);
    }

    public static String getBusinessName() {
        return CONTEXT_HOLDER.get();
    }

    public static void clearBusinessName() {
        CONTEXT_HOLDER.remove();
    }
}
