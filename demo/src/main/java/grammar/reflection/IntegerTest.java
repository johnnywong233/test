package grammar.reflection;

import java.lang.reflect.Field;
import java.util.Random;

/**
 * Created by wajian on 2016/10/5.
 */
public class IntegerTest {
    //http://www.mincoder.com/article/4432.shtml
    //Integer由缓存决定，改变缓存来改变Integer
    public static void main(String[] args) throws Exception {
        Class<?> clazz = Class.forName("java.lang.Integer$IntegerCache");
        Field field = clazz.getDeclaredField("cache");
        field.setAccessible(true);
        Integer[] cache = (Integer[]) field.get(clazz);
        // 改变Integer的缓存
        for (int i = 0; i < cache.length; i++) {
            cache[i] = new Random().nextInt(cache.length);
        }
        System.out.println("Cache size for integer type: "+ cache.length);
        for (int i = 0; i < 10; i++) {
            System.out.println((Integer) i); //这个时候1不是1 ，2也不是2
        }
    }
}
