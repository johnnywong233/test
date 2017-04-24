import ehcache.bean.DemoInfo;
import javassist.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ehcache.service.DemoInfoService;

import javax.annotation.Resource;

/**
 * Author: Johnny
 * Date: 2017/4/18
 * Time: 0:11
 */
@RunWith(SpringJUnit4ClassRunner.class)
//指定SpringBoot工程的Application启动类
//@SpringApplicationConfiguration(classes = App.class)
//由于是Web项目，Junit需要模拟ServletContext，因此需要给测试类加上@WebAppConfiguration。
@WebAppConfiguration
public class AppTest {

    @Resource
    private DemoInfoService demoInfoService;

    @Test
    public void testCache() {
        //存入两条数据.
        DemoInfo demoInfo = new DemoInfo();
        demoInfo.setName("张三");
        demoInfo.setPwd("123456");
        DemoInfo demoInfo2 = demoInfoService.save(demoInfo);

        //不走缓存.
        System.out.println(demoInfoService.findById(demoInfo2.getId()));
        //走缓存.
        System.out.println(demoInfoService.findById(demoInfo2.getId()));

        demoInfo = new DemoInfo();
        demoInfo.setName("李四");
        demoInfo.setPwd("123456");
        DemoInfo demoInfo3 = demoInfoService.save(demoInfo);

        //不走缓存.
        System.out.println(demoInfoService.findById(demoInfo3.getId()));
        //走缓存.
        System.out.println(demoInfoService.findById(demoInfo3.getId()));

        System.out.println("============修改数据=====================");
        DemoInfo updated = new DemoInfo();
        updated.setName("李四-updated");
        updated.setPwd("123456");
        updated.setId(demoInfo3.getId());
        try {
            System.out.println(demoInfoService.update(updated));
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        //不走缓存.
        System.out.println(demoInfoService.findById(updated.getId()));
        //走缓存.
//    	System.out.println(demoInfoService.findById(updated.getId()));
    }
}