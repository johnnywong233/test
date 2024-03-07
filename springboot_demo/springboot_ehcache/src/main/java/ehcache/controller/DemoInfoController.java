package ehcache.controller;

import ehcache.bean.DemoInfo;
import ehcache.service.DemoInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Author: Johnny
 * Date: 2017/4/17
 * Time: 23:34
 */
@Slf4j
@RestController
public class DemoInfoController {
    @Resource
    private DemoInfoService demoInfoService;

    @RequestMapping("/test")
    public String test() {
        DemoInfo demoInfo = new DemoInfo();
        demoInfo.setName("张三");
        demoInfo.setPwd("123456");
        DemoInfo demoInfo2 = demoInfoService.save(demoInfo);

        // 不走缓存.
        System.out.println(demoInfoService.findById(demoInfo2.getId()));
        // 走缓存.
        System.out.println(demoInfoService.findById(demoInfo2.getId()));

        demoInfo = new DemoInfo();
        demoInfo.setName("李四");
        demoInfo.setPwd("123456");
        DemoInfo demoInfo3 = demoInfoService.save(demoInfo);

        // 不走缓存.
        System.out.println(demoInfoService.findById(demoInfo3.getId()));
        // 走缓存.
        System.out.println(demoInfoService.findById(demoInfo3.getId()));

        System.out.println("============update data=====================");
        DemoInfo updated = new DemoInfo();
        updated.setName("李四-updated");
        updated.setPwd("123456");
        updated.setId(demoInfo3.getId());
        System.out.println(demoInfoService.update(updated));
        // 不走缓存.
        System.out.println(demoInfoService.findById(updated.getId()));
        return "ok";
    }
}
