package mockito;

import org.apache.commons.lang3.StringUtils;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Author: Johnny
 * Date: 2017/3/9
 * Time: 9:40
 * http://zhihao.info/sd/programm-language/java/junit_abstract_class_method/
 * Junit怎么测试抽象类中的方法？
 * 可以继承抽象类进行相关方法的测试，为了方便测试，一般在测试类内部使用静态内部类来实现抽象方法进行测试。
 */
@RunWith(PowerMockRunner.class)
public class AbstractClassDemo {

    private DataService dataService;

    @BeforeClass
    public void setUp() {
        dataService = new AbstractClassDemo.InnerDataService().getDataService();
    }

    static class InnerDataService extends DataService {
        DataService getDataService() {
            return new InnerDataService();
        }
    }

    @Test
    public void valueIsNotBlankAddToMap_value_is_null_not_add_to_map() {
        Map<String, String> map = new HashMap<>();
        String key = "test";
        String value = null;
        dataService.valueIsNotBlankAddToMap(key, value, map);

        assertEquals(null, map.get(key));
    }
}

/**
 * this is the abstract class to be tested
 */
abstract class DataService {
    private void valueAddToMap(String key, String value, Map<String, String> map, boolean isAddNull) {
        if (isAddNull || StringUtils.isNotBlank(value)) {
            map.put(key, value);
        }
    }

    void valueIsNotBlankAddToMap(String key, String value, Map<String, String> map) {
        valueAddToMap(key, value, map, false);
    }
}
