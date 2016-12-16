package grammar.collection;

import java.util.*;

/**
 * Created by wajian on 2016/8/16.
 */
public class HashMapKeySortDemo {
    public static void main(String[] args) {
        Map<String, String> map = new TreeMap<String, String>(
                new Comparator<String>() {
                    public int compare(String obj1, String obj2) {
                        //desc sort
                        return obj2.compareTo(obj1);
                    }
                });
        map.put("b", "ccccc");
        map.put("d", "aaaaa");
        map.put("c", "bbbbb");
        map.put("a", "ddddd");

        Set<String> keySet = map.keySet();
        Iterator<String> iter = keySet.iterator();
        
        while (iter.hasNext()) {
            String key = iter.next();
            System.out.println(key + ":" + map.get(key));
        }
    }
}
