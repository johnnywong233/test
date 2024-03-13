package utils;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.collections.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.*;

/**
 * 通用序列化辅助类
 * 用于纵表（xx_extend）的序列化和反序列化
 * Created by Johnny on 2018/1/2.
 */
@Slf4j
public class SerilizationUtils {

    private static final String KEY = "key";

    private static final String VALUE = "value";

    private static final Integer ERR_SERIALIZATION_FAIL_CODE = 1001;

    private static final String ERR_SERIALIZATION_FAIL_MSG = "序列化到纵表失败";

    private static final Integer ERR_DESERIALIZATION_FAIL_CODE = 1002;

    private static final String ERR_DESERIALIZATION_FAIL_MSG = "从纵表装配目标类失败";

    /**
     * 装配属性到key&value类
     */
    public static <T> List<T> buildExtendClz(Object rawObject, Class<T> targetClz) throws Exception {
        if (!checkKeyValueFieldExist(targetClz)) {
            return null;
        }

        try {
            // 导出字段，生产包含的key和value目标类
            List<T> result = new ArrayList<>();

            Field[] fields = rawObject.getClass().getDeclaredFields();
            for (Field field : fields) {
                String key = field.getName();
                Method method = rawObject.getClass().getMethod("get" + key.substring(0, 1).toUpperCase() + key.substring(1));
                Object invoke = method.invoke(rawObject);

                if (invoke != null) {
                    String value;
                    if (field.isAnnotationPresent(KVSerilizable.class)) {
                        value = JSON.toJSONString(invoke);
                    } else {
                        value = String.valueOf(invoke);
                    }

                    // 生成目标类
                    T target = targetClz.getDeclaredConstructor().newInstance();
                    // 目前key和value均为String类型，以后可扩展支持更多类型
                    Method setKeyMethod = target.getClass().getMethod("setKey", String.class);
                    Method setValueMethod = targetClz.getDeclaredMethod("setValue", String.class);

                    setKeyMethod.invoke(target, key);
                    setValueMethod.invoke(target, value);

                    result.add(target);
                }
            }
            return result;
        } catch (Exception e) {
            log.error("无法序列为domain类" + targetClz.getName());
            throw new Exception();
        }
    }

    /**
     * 校验target类是否有key和value域
     */
    private static boolean checkKeyValueFieldExist(Class<?> targetClz) {
        boolean result = true;
        // 校验target类是否有key和value域
        Field[] rawFields = targetClz.getDeclaredFields();
        boolean hasKey = false;
        boolean hasValue = false;

        for (Field field : rawFields) {
            if (field.getName().equals(KEY)) {
                hasKey = true;
            }
            if (field.getName().equals(VALUE)) {
                hasValue = true;
            }
        }

        if (!hasKey || !hasValue) {
            result = false;
            log.error("目标输出类不包含key或value字段");
        }
        return result;
    }

    /**
     * 从key&value表读取到Bo层对象
     */
    private static <T> T parseKVMapToObject(Map<String, Object> map, Class<T> beanClass) throws Exception {
        if (map == null) {
            return null;
        }
        T obj = beanClass.getDeclaredConstructor().newInstance();

        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            int mod = field.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                continue;
            }

            field.setAccessible(true);
            Class<?> targetClazz;
            Class<?> fieldClazz = field.getType();
            if (field.isAnnotationPresent(KVSerilizable.class)) {
                if (fieldClazz.isAssignableFrom(List.class) || fieldClazz.isAssignableFrom(Map.class)) {
                    Type genericType = field.getGenericType();

                    String jsonString = (String) map.get(field.getName());
                    Object valueAfter = JSON.parseObject(jsonString, genericType);

                    field.set(obj, valueAfter);
                } else {
                    targetClazz = field.getType();

                    String jsonString = (String) map.get(field.getName());
                    Object valueAfter = JSON.parseObject(jsonString, targetClazz);

                    field.set(obj, valueAfter);
                }
            } else {
                Object valueAfter;
                if (fieldClazz.isAssignableFrom(Date.class)) {
                    valueAfter = DateUtil.convert2Date((String) map.get(field.getName()), DateUtil.STANDARD_DATE_TIME_FORMAT);
                } else {
                    valueAfter = ConvertUtils.convert(map.get(field.getName()), fieldClazz);
                }

                field.set(obj, valueAfter);
            }
        }
        return obj;
    }

    /**
     * 从key&value表读取到Bo层对象重载方法，输入为KeyPair结构
     */
    public static <T> T parseKVListToObject(List<?> list, Class<T> beanClass) throws Exception {
        try {
            if (CollectionUtils.isEmpty(list)) {
                return null;
            }

            Map<String, Object> keyValueMap = new HashMap<>();
            for (Object element : list) {
                Field[] fields = element.getClass().getDeclaredFields();
                String key = null;
                Object value = null;
                for (Field field : fields) {
                    field.setAccessible(true);
                    if (field.getName().equals(KEY)) {
                        key = (String) field.get(element);
                    }
                    if (field.getName().equals(VALUE)) {
                        value = field.get(element);
                    }
                }

                if (key == null || value == null) {
                    log.error("无法从类{}中解析key和value", element.getClass().getName());
                } else {
                    keyValueMap.put(key, value);
                }
            }
            return parseKVMapToObject(keyValueMap, beanClass);
        } catch (Exception e) {
            log.error("解析为bean{}失败", beanClass.getName());
            throw new Exception();
        }
    }

    /**
     * 获得Class的声明字段
     */
    public static List<String> getDeclaredFields(Class<?> clz) {
        if (clz != null) {
            List<String> fieldList = new ArrayList<>();

            Field[] fields = clz.getDeclaredFields();
            for (Field field : fields) {
                fieldList.add(field.getName());
            }
            return fieldList;
        }

        return null;
    }

}