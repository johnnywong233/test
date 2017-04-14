package algorithm;

/**
 * Author: Johnny Date: 2016/10/14 Time: 0:42
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class Tuple<T> {
    private static <T> Tuple make(T... args) {
        return new Tuple(args);
    }

    private T[] items;

    private Tuple(T[] items) {
        this.items = items;
    }

    private T index(int index) {
        if (index < 0 || items == null || index > items.length - 1) {
            return null;
        }
        return items[index];
    }

    // http://unmi.cc/simple-java-tuple-datatype/
    // http://sethjust.com/2012/11/17/java-tuples/
    public static void main(String[] args) {
        Tuple<String> t = Tuple.make("Unmi", "fantasia@sina.come", "demo");
        System.out.println(t.index(2));
    }

}
