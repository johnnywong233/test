package aop;

import aop.service.MenuService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Author: Johnny
 * Date: 2017/10/7
 * Time: 18:16
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CacheTest {
    @Autowired
    MenuService menuService;

    @Test
    public void testCache() {
        System.out.println("call:" + menuService.getMenuList());
        System.out.println("call:" + menuService.getMenuList());
    }

    //测试这个方法时，需要使用ApplicationContextHolder，否则
    @Test
    public void testInnerCall(){
        System.out.println("call:"+menuService.getRecommends());
        System.out.println("call:"+menuService.getRecommends());
    }
}
