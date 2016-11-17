package simpletest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Author: Johnny
 * Date: 2016/10/14
 * Time: 0:34
 */
public class OrderedProperties extends Properties {
    //http://unmi.cc/ordered-java-properties-class/
    public static void main(String[] args) throws IOException {
        Properties props = new OrderedProperties();
        FileInputStream fis = new FileInputStream(new File(""));
        props.load(fis);
        //通过 keys(), keySet() 或 stringPropertyNames 来遍历都能保证按文件中的顺序输出
    }

    private static final long serialVersionUID = -4627607243846121965L;

    private final LinkedHashSet<Object> keys = new LinkedHashSet<>();

    public Enumeration<Object> keys() {
        return Collections.enumeration(keys);
    }

    public Object put(Object key, Object value) {
        keys.add(key);
        return super.put(key, value);
    }

    public Set<Object> keySet() {
        return keys;
    }

    public Set<String> stringPropertyNames() {
        Set<String> set = this.keys.stream().map(key -> (String) key).collect(Collectors.toCollection(LinkedHashSet::new));
        return set;
    }
}
