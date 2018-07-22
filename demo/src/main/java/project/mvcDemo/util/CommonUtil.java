package project.mvcDemo.util;

import java.awt.Color;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public final class CommonUtil {
    private static final List<String> PATTERN = new ArrayList<>();
    private static final List<TypeConverter> CONVERTERS = new ArrayList<>();

    static {
        PATTERN.add("yyyy-MM-dd");
        PATTERN.add("yyyy-MM-dd HH:mm:ss");
    }

    private CommonUtil() {
        throw new AssertionError();
    }

    /**
     * 将字符串的首字母大写
     */
    public static String capitalize(String str) {
        StringBuilder sb = new StringBuilder();
        if (str != null && str.length() > 0) {
            sb.append(str.substring(0, 1).toUpperCase());
            if (str.length() > 1) {
                sb.append(str.substring(1));
            }
            return sb.toString();
        }
        return str;
    }

    /**
     * 生成随机颜色
     */
    public static Color getRandomColor() {
        Random random = new Random();

        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        return new Color(r, g, b);
    }

    /**
     * 添加时间日期样式
     *
     * @param pattern 时间日期样式
     */
    public static void registerDateTimePattern(String pattern) {
        PATTERN.add(pattern);
    }

    /**
     * 取消时间日期样式
     *
     * @param pattern 时间日期样式
     */
    public static void unRegisterDateTimePattern(String pattern) {
        PATTERN.remove(pattern);
    }

    /**
     * 添加类型转换器
     *
     * @param converter 类型转换器对象
     */
    public static void registerTypeConverter(TypeConverter converter) {
        CONVERTERS.add(converter);
    }

    /**
     * 取消类型转换器
     *
     * @param converter 类型转换器对象
     */
    public static void unRegisterTypeConverter(TypeConverter converter) {
        CONVERTERS.remove(converter);
    }

    /**
     * 将字符串转换成时间日期类型
     *
     * @param str 时间日期字符串
     */
    private static Date convertStringToDateTime(String str) {
        if (str != null) {
            for (String pattern : PATTERN) {
                Date date = tryConvertStringToDate(str, pattern);

                if (date != null) {
                    return date;
                }
            }
        }
        return null;
    }

    /**
     * 按照指定样式将时间日期转换成字符串
     *
     * @param date    时间日期对象
     * @param pattern 样式字符串
     * @return 时间日期的字符串形式
     */
    public static String convertDateTimeToString(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }

    private static Date tryConvertStringToDate(String str, String pattern) {
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        // 不允许将不符合样式的字符串转换成时间日期
        dateFormat.setLenient(false);

        try {
            return dateFormat.parse(str);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 将字符串值按指定的类型转换成转换成对象
     *
     * @param elemType 类型
     * @param value    字符串值
     */
    public static Object changeStringToObject(Class<?> elemType, String value) {
        Object tempObj = null;

        if (elemType == byte.class || elemType == Byte.class) {
            tempObj = Byte.parseByte(value);
        } else if (elemType == short.class || elemType == Short.class) {
            tempObj = Short.parseShort(value);
        } else if (elemType == int.class || elemType == Integer.class) {
            tempObj = Integer.parseInt(value);
        } else if (elemType == long.class || elemType == Long.class) {
            tempObj = Long.parseLong(value);
        } else if (elemType == double.class || elemType == Double.class) {
            tempObj = Double.parseDouble(value);
        } else if (elemType == float.class || elemType == Float.class) {
            tempObj = Float.parseFloat(value);
        } else if (elemType == boolean.class || elemType == Boolean.class) {
            tempObj = Boolean.parseBoolean(value);
        } else if (elemType == java.util.Date.class) {
            tempObj = convertStringToDateTime(value);
        } else if (elemType == java.lang.String.class) {
            tempObj = value;
        } else {
            for (TypeConverter converter : CONVERTERS) {
                try {
                    tempObj = converter.convert(elemType, value);
                    if (tempObj != null) {
                        return tempObj;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return tempObj;
    }

    /**
     * 获取文件后缀名
     *
     * @param filename 文件名
     * @return 文件的后缀名以.开头
     */
    public static String getFileSuffix(String filename) {
        int index = filename.lastIndexOf(".");
        return index > 0 ? filename.substring(index) : "";
    }
}
