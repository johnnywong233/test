package cache;

public class CacheTest {
    //http://www.codeceo.com/article/java-cache-ways.html
    public static void main(String[] args) {
        System.out.println(CacheManager.getSimpleFlag("alksd"));
        CacheManager.putCache("abc", new Cache());
        CacheManager.putCache("def", new Cache());
        CacheManager.putCache("ccc", new Cache());
        CacheManager.clearOnly("");
        Cache c = new Cache();
        for (int i = 0; i < 10; i++) {
            CacheManager.putCache("" + i, c);
        }
        CacheManager.putCache("aaaaaaaa", c);
        CacheManager.putCache("abchcy;alskd", c);
        CacheManager.putCache("cccccccc", c);
        CacheManager.putCache("abcoqiwhcy", c);
        System.out.println("size before delete:" + CacheManager.getCacheSize());
        CacheManager.getCacheAllKey();
        CacheManager.clearAll("aaaa");
        System.out.println("size after delete:" + CacheManager.getCacheSize());
        CacheManager.getCacheAllKey();
    }
}
