package simpletest.OOMdemo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by johnny on 2016/9/16.
 * Demo on OOM
 */
public class Demo1 {

    private static class Key {
        Integer id;

        Key(Integer id) {
            this.id = id;
        }

        @Override
        public int hashCode() {
            return id.hashCode();
        }
        
        //must override both hashCode and equals, or as here OOM
        @Override
        public boolean equals(Object o) {
          boolean response = false;
          if (o instanceof Key) {
           response = (((Key)o).id).equals(this.id);
          }
          return response;
        }
    }

    //http://www.jb51.net/article/69306.htm
    public static void main(String[] args) {
        Map<Key, String> m = new HashMap<>();
        while (true)
            for (int i = 0; i < 10000; i++)
                if (!m.containsKey(i))
                    m.put(new Key(i), "Number:" + i);
    }

}