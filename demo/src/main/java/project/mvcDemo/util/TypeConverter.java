package project.mvcDemo.util;

public interface TypeConverter {
    Object convert(Class<?> elemType, String value) throws Exception;
}
