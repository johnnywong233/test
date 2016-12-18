package simpletest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.junit.Test;

/**
 * Created by johnny on 2016/10/6.
 * usage of method unmodifiableCollection
 */
public class CollectionsTest {
    @Test
    public void test() {
        Collection<String> c = new ArrayList<>();
        //get a copy of original collection, and s is final object,
        //modify it will throw a java.lang.@link UnsupportedOperationException @see 
        Collection<String> s = Collections.unmodifiableCollection(c);
        c.add("str");
        //change the original collection, the copied one will be change as
        System.out.println(s);
    }
}
