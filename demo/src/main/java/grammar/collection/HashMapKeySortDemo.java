package grammar.collection;

import java.util.*;

/**
 * Created by wajian on 2016/8/16.
 */
public class HashMapKeySortDemo {
    public static void main(String[] args) {
        Map<String, String> map = new TreeMap<>(
                (obj1, obj2) -> {
                    //desc sort
                    return obj2.compareTo(obj1);
                });
        map.put("b", "ccccc");
        map.put("d", "aaaaa");
        map.put("c", "bbbbb");
        map.put("a", "ddddd");

        Set<String> keySet = map.keySet();

        for (String key : keySet) {
            System.out.println(key + ":" + map.get(key));
        }
    }
}
