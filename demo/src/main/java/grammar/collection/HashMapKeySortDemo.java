package grammar.collection;

import java.util.*;

/**
 * Created by johnny on 2016/8/16.
 */
public class HashMapKeySortDemo {
    public static void main(String[] args) {
        Map<String, String> map = new TreeMap<>(Comparator.reverseOrder());
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
