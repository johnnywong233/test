package json.jackson;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;

/**
 * Author: Johnny
 * Date: 2017/1/3
 * Time: 21:29
 */
public class MyNameStrategy extends PropertyNamingStrategy {
    @Override
    public String nameForField(MapperConfig config, AnnotatedField field, String defaultName) {
        return convert(defaultName);
    }

    @Override
    public String nameForGetterMethod(MapperConfig config, AnnotatedMethod method, String defaultName) {
        return convert(defaultName);
    }

    @Override
    public String nameForSetterMethod(MapperConfig config, AnnotatedMethod method, String defaultName) {
        return convert(defaultName);
    }

    public String convert(String defaultName) {
        char[] arr = defaultName.toCharArray();
        if (arr.length != 0) {
            if (Character.isLowerCase(arr[0])) {
                char upper = Character.toUpperCase(arr[0]);
                arr[0] = upper;
            }
        }
        return String.valueOf(arr);
    }

}
