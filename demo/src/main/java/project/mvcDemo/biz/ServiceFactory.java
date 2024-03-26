package project.mvcDemo.biz;

import project.mvcDemo.util.CommonUtil;

import java.util.HashMap;
import java.util.Map;

public class ServiceFactory {
    private static final String DEFAULT_IMPL_PACKAGE_NAME = "impl";

    private static final Map<Class<?>, Object> map = new HashMap<>();

    /**
     * 工厂方法
     *
     * @param type 业务逻辑对象的类型
     * @return 业务逻辑对象的代理对象
     */
    public static synchronized Object factory(Class<?> type) {
        if (map.containsKey(type)) {
            return map.get(type);
        } else {
            try {
                Object serviceObj = Class.forName(
                        type.getPackage().getName() + "." + DEFAULT_IMPL_PACKAGE_NAME + "."
                                + type.getSimpleName() + CommonUtil.capitalize(DEFAULT_IMPL_PACKAGE_NAME)).getDeclaredConstructor().newInstance();
                map.put(type, ServiceProxy.getProxyInstance(serviceObj));
                return serviceObj;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
