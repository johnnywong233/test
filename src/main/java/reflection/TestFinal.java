package reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Created by wajian on 2016/10/12.
 *
 */
public class TestFinal {
    //http://unmi.cc/java-reflection-modify-final-field-value/
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
    	//reflect the private field
//        Field nameField = OneCity.class.getDeclaredField("name");
//        nameField.setAccessible(true);
//        nameField.set(null, "Shenzhen");
//        System.out.println(OneCity.getName());
        
    	//reflect the final and private field
        Field nameField = TwoCity.class.getDeclaredField("name");

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(nameField, nameField.getModifiers() & ~Modifier.FINAL); //②

        nameField.setAccessible(true); //这个同样不能少，除非上面把 private 也拿掉了，可能还得 public
        nameField.set(null, "Shenzhen");
        System.out.println(nameField.get(null));//stay the same
        System.out.println(TwoCity.getName());//see the diff when change name to "Beijing"
        
        
    }

}


class OneCity {
    private static String name = "Beijing";
    public static String getName() {
        return name;
    }
}

class TwoCity {
    private static final String name = new String("Beijing");
//    private static final String name = "Beijing";
    public static String getName() {
        return name;
    }
}