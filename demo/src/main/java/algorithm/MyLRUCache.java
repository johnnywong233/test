package algorithm;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Author: Johnny
 * Date: 2017/1/24
 * Time: 21:47
 * http://www.importnew.com/16264.html
 */
public class MyLRUCache<K, V> extends LinkedHashMap<K, V> {
    private int cacheSize;

    public MyLRUCache(int cacheSize) {
        super(16, 0.75f, true);
        this.cacheSize = cacheSize;
    }

    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() >= cacheSize;
    }
}
