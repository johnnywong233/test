package utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.google.common.collect.Lists;
import log.LogUtil;
import org.apache.commons.collections.CollectionUtils;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by johnny on 2016/10/6.
 * convert all kinds of java objects into JSON
 * http://blog.csdn.net/ycwol/article/details/39202753
 */
public class JsonUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
    }

    public static String stringToJson(String s) {
        if (s == null) {
            return nullToJson();
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            switch (ch) {
                case '"':
                    sb.append("\\\"");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                case '/':
                    sb.append("\\/");
                    break;
                default:
                    if (ch <= '\u001F') {
                        String ss = Integer.toHexString(ch);
                        sb.append("\\u");
                        for (int k = 0; k < 4 - ss.length(); k++) {
                            sb.append('0');
                        }
                        sb.append(ss.toUpperCase());
                    } else {
                        sb.append(ch);
                    }
            }
        }
        return sb.toString();
    }

    public static String nullToJson() {
        return "";
    }

    public static String objectToJson(Object obj) {
        StringBuilder json = new StringBuilder();
        if (obj == null) {
            json.append("\"\"");
        } else if (obj instanceof Number) {
            json.append(numberToJson((Number) obj));
        } else if (obj instanceof Boolean) {
            json.append(booleanToJson((Boolean) obj));
        } else if (obj instanceof String) {
            json.append("\"").append(stringToJson(obj.toString())).append("\"");
        } else if (obj instanceof Object[]) {
            json.append(arrayToJson((Object[]) obj));
        } else if (obj instanceof List) {
            json.append(listToJson((List<?>) obj));
        } else if (obj instanceof Map) {
            json.append(mapToJson((Map<?, ?>) obj));
        } else if (obj instanceof Set) {
            json.append(setToJson((Set<?>) obj));
        } else {
            json.append(beanToJson(obj));
        }
        return json.toString();
    }

    public static String numberToJson(Number number) {
        return number.toString();
    }

    public static String booleanToJson(Boolean bool) {
        return bool.toString();
    }

    /**
     * @param bean bean object
     * @return String
     */
    public static String beanToJson(Object bean) {
        StringBuilder json = new StringBuilder();
        json.append("{");
        PropertyDescriptor[] props = null;
        try {
            props = Introspector.getBeanInfo(bean.getClass(), Object.class)
                    .getPropertyDescriptors();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        if (props != null) {
            for (PropertyDescriptor prop : props) {
                try {
                    String name = objectToJson(prop.getName());
                    String value = objectToJson(prop.getReadMethod()
                            .invoke(bean));
                    json.append(name);
                    json.append(":");
                    json.append(value);
                    json.append(",");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            json.setCharAt(json.length() - 1, '}');
        } else {
            json.append("}");
        }
        return json.toString();
    }

    /**
     * @param list list object
     * @return String
     */
    public static String listToJson(List<?> list) {
        StringBuilder json = new StringBuilder();
        json.append("[");
        if (!CollectionUtils.isEmpty(list)) {
            for (Object obj : list) {
                json.append(objectToJson(obj));
                json.append(",");
            }
            json.setCharAt(json.length() - 1, ']');
        } else {
            json.append("]");
        }
        return json.toString();
    }

    /**
     * @param array array of object
     * @return String
     */
    public static String arrayToJson(Object[] array) {
        StringBuilder json = new StringBuilder();
        json.append("[");
        if (array != null && array.length > 0) {
            for (Object obj : array) {
                json.append(objectToJson(obj));
                json.append(",");
            }
            json.setCharAt(json.length() - 1, ']');
        } else {
            json.append("]");
        }
        return json.toString();
    }

    /**
     * @param map map object
     * @return String
     */
    public static String mapToJson(Map<?, ?> map) {
        StringBuilder json = new StringBuilder();
        json.append("{");
        if (map != null && map.size() > 0) {
            for (Object key : map.entrySet()) {
                json.append(objectToJson(key));
                json.append(":");
                json.append(objectToJson(map.get(key)));
                json.append(",");
            }
            json.setCharAt(json.length() - 1, '}');
        } else {
            json.append("}");
        }
        return json.toString();
    }

    /**
     * @param set set object
     * @return String
     */
    public static String setToJson(Set<?> set) {
        StringBuilder json = new StringBuilder();
        json.append("[");
        if (!CollectionUtils.isEmpty(set)) {
            for (Object obj : set) {
                json.append(objectToJson(obj));
                json.append(",");
            }
            json.setCharAt(json.length() - 1, ']');
        } else {
            json.append("]");
        }
        return json.toString();
    }

    /**
     * 把对像转换成json 字符串
     */
    public static <T extends Object> String toJson(T object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            LogUtil.error("JsonUtility.toJson转换异常", e);
            return "";
        }
    }

    /**
     * 反序列化得到Pojo
     */
    public static <T> T toObject(String json, Class<T> type) {
        try {
            return objectMapper.readValue(json, type);
        } catch (Exception e) {
            LogUtil.error("反序列化对象失败", e);
        }
        return null;
    }

    /**
     * 对象字段maping
     */
    public static <T> T mapObject(Class<T> clazz, Object o) {
        String json = toJson(o);
        Object res = toObject(json, clazz);
        return (T) res;
    }

    /**
     * 反序列化得到List<Pojo>
     */
    public static <T> List<T> toOjectList(String json, Class<T> type) {
        try {
            CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, type);
            return objectMapper.readValue(json, listType);
        } catch (Exception e) {
            LogUtil.error("反序列化对象列表失败", e);
        }
        return Lists.newArrayList();
    }
}
