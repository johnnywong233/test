package algorithm;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Author: Johnny
 * Date: 2017/1/24
 * Time: 21:47
 * <a href="http://www.importnew.com/16264.html">...</a>
 */
public class MyLRUCache<K, V> extends LinkedHashMap<K, V> {
    private final int cacheSize;

    public MyLRUCache(int cacheSize) {
        super(16, 0.75f, true);
        this.cacheSize = cacheSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() >= cacheSize;
    }
}
