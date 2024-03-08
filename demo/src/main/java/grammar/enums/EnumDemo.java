package grammar.enums;

import utils.PackageUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Johnny
 * Date: 2017/8/20
 * Time: 23:43
 */
public class EnumDemo {

    //http://www.cnblogs.com/draem0507/p/4110987.html
    public static void main(String[] args) {

        System.out.println(getEnumObject(2, AuditNotifyStatus.class).getMessage());//短信
        System.out.println(getEnumObject(6, AuditNotifyStatus.class).getMessage());//短信和邮箱

        System.out.println(getEnumObject(2, AccountStatus.class).getMessage());//code
    }

    @SuppressWarnings("unchecked")
    private static <T extends EnumMessage> T getEnumObject(int value, Class<T> clazz) {
        return (T) Constant.ENUM_MAP.get(clazz).get(value);
    }

}

class Constant {
    /**
     * 枚举类对应的包路径
     */
    private final static String PACKAGE_NAME = "grammar.enums";
    /**
     * 枚举接口类全路径
     */
    private final static String ENUM_MESSAGE_PATH = PACKAGE_NAME + ".EnumMessage";

    /**
     * 枚举类对应的全路径集合
     */
    private static final List<String> ENUM_OBJECT_PATH = PackageUtil.getPackageClasses(PACKAGE_NAME, true);

    /**
     * 存放单个枚举对象 map常量定义
     */
    private static Map<Integer, EnumMessage> SINGLE_ENUM_MAP = null;
    /**
     * 所有枚举对象的 map
     */
    static final Map<Class<?>, Map<Integer, EnumMessage>> ENUM_MAP = initialEnumMap(true);

    /**
     * 加载所有枚举对象数据
     *
     * @param isForceCheck 是否强制校验枚举是否实现了EnumMessage接口
     */
    private static Map<Class<?>, Map<Integer, EnumMessage>> initialEnumMap(boolean isForceCheck) {
        Map<Class<?>, Map<Integer, EnumMessage>> enumMap = new HashMap<>();
        try {
            for (String classname : ENUM_OBJECT_PATH) {
                Class<?> cls;
                cls = Class.forName(classname);
                Class<?>[] iter = cls.getInterfaces();
                boolean flag = false;
                if (isForceCheck) {
                    for (Class<?> cz : iter) {
                        if (cz.getName().equals(ENUM_MESSAGE_PATH)) {
                            flag = true;
                            break;
                        }
                    }
                }
                if (flag == isForceCheck) {
                    SINGLE_ENUM_MAP = new HashMap<>();
                    initialSingleEnumMap(cls);
                    enumMap.put(cls, SINGLE_ENUM_MAP);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return enumMap;
    }

    /**
     * 加载每个枚举对象数据
     */
    private static void initialSingleEnumMap(Class<?> cls) throws Exception {
        Method method = cls.getMethod("values");
        EnumMessage[] inter = (EnumMessage[]) method.invoke(null, null);
        for (EnumMessage enumMessage : inter) {
            SINGLE_ENUM_MAP.put(enumMessage.getValue(), enumMessage);
        }
    }
}