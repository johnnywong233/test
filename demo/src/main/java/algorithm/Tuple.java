package algorithm;

/**
 * Author: Johnny
 * Date: 2016/10/14
 * Time: 0:42
 */
public class Tuple<T> {
    //TODO
    private static <T> Tuple mk(T... args){
        return new Tuple(args);
    }

    private T[] items;

    private Tuple(T[] items) {
        this.items = items;
    }

    private T _(int index){
        if(index < 0 || items == null || index > items.length-1){
            return null;
        }
        return items[index];
    }

    //http://unmi.cc/simple-java-tuple-datatype/
    public static void main(String[] args) {
        Tuple<String> t = Tuple.mk("Unmi","fantasia@sina.come");
        System.out.println(t._(0)); //输出 Unmi
    }

}
