package simpletest;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * the original java properties is extended from HashTable, which do not make sure the order
 * Author: Johnny
 * Date: 2016/10/14
 * Time: 0:34
 */
@Slf4j
public class OrderedProperties extends Properties {
    // http://stackoverflow.com/questions/1312383/pulling-values-from-a-java-properties-file-in-order
    // https://github.com/playframework/play1/blob/master/framework/src/play/utils/OrderSafeProperties.java
    // http://livedocs.adobe.com/jrun/4/javadocs/jrunx/util/OrderedProperties.html
    // http://www.openrdf.org/doc/alibaba/2.0-rc4/apidocs/org/openrdf/repository/object/composition/helpers/OrderedProperties.html
    private final LinkedHashSet<Object> keys = new LinkedHashSet<>();

    public static void main(String[] args) throws IOException {
        Properties props = new OrderedProperties();
        FileInputStream fis = new FileInputStream("kaptcha.properties");
        props.load(fis);
        log.info("props: {}", props);
        //通过 keys(), keySet() 或 stringPropertyNames 来遍历都能保证按文件中的顺序输出
    }

    @Override
    public Enumeration<Object> keys() {
        return Collections.enumeration(keys);
    }

    @Override
    public Object put(Object key, Object value) {
        keys.add(key);
        return super.put(key, value);
    }

    @Override
    public Set<Object> keySet() {
        return keys;
    }

    @Override
    public Set<String> stringPropertyNames() {
        return this.keys.stream().map(key -> (String) key).collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
