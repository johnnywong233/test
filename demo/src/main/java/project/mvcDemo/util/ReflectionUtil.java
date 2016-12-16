package project.mvcDemo.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class ReflectionUtil {
	private ReflectionUtil() {
		throw new AssertionError();
	}
	
	/**
	 * 根据字段名查找字段的类型
	 * @param target 目标对象
	 * @param fieldName 字段名
	 * @return 字段的类型
	 */
	public static Class<?> getFieldType(Object target, String fieldName) {
		Class<?> clazz = target.getClass();
		String[] fs = fieldName.split("\\.");
		
		try {
			for(int i = 0; i < fs.length - 1; i++) {
				Field f = clazz.getDeclaredField(fs[i]);
				target = f.getType().newInstance();
				clazz = target.getClass();
			}
			return clazz.getDeclaredField(fs[fs.length - 1]).getType();
		}
		catch(Exception e) {
		        // throw new RuntimeException(e);
		}
		return null;
	}
	
	/**
	 * 获取对象所有字段的名字
	 * @param obj 目标对象
	 * @return 字段名字的数组
	 */
	public static String[] getFieldNames(Object obj) {
		Class<?> clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields();
		List<String> fieldNames = new ArrayList<>();
		for(int i = 0; i < fields.length; i++) {
			if((fields[i].getModifiers() & Modifier.STATIC) == 0) {
				fieldNames.add(fields[i].getName());
			}
		}
		return fieldNames.toArray(new String[fieldNames.size()]);
	}

	/**
	 * 通过反射取对象指定字段(属性)的值
	 * @param target 目标对象
	 * @param fieldName 字段的名字
	 * @throws 如果取不到对象指定字段的值则抛出异常
	 * @return 字段的值
	 */
	public static Object getValue(Object target, String fieldName) {
		Class<?> clazz = target.getClass();
		String[] fs = fieldName.split("\\.");
		
		try {
			for(int i = 0; i < fs.length - 1; i++) {
				Field f = clazz.getDeclaredField(fs[i]);
				f.setAccessible(true);
				target = f.get(target);
				clazz = target.getClass();
			}
		
			Field f = clazz.getDeclaredField(fs[fs.length - 1]);
			f.setAccessible(true);
			return f.get(target);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 通过反射给对象的指定字段赋值
	 * @param target 目标对象
	 * @param fieldName 字段的名称
	 * @param value 值
	 */
	public static void setValue(Object target, String fieldName, Object value) {
		Class<?> clazz = target.getClass();
		String[] fs = fieldName.split("\\.");
		try {
			for(int i = 0; i < fs.length - 1; i++) {
				Field f = clazz.getDeclaredField(fs[i]);
				f.setAccessible(true);
				Object val = f.get(target);
				if(val == null) {
					Constructor<?> c = f.getType().getDeclaredConstructor();
					c.setAccessible(true);
					val = c.newInstance();
					f.set(target, val);
				}
				target = val;
				clazz = target.getClass();
			}
		
			Field f = clazz.getDeclaredField(fs[fs.length - 1]);
			f.setAccessible(true);
			f.set(target, value);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
