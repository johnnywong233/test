package demo1.service;

import log.annotation.Log;
import org.springframework.stereotype.Service;

/**
 * Author: Johnny
 * Date: 2017/4/13
 * Time: 20:09
 */
@Service
public class TestLogStarterService {
    @Log
    public void test(int num) {
        System.out.println("----test---- " + num);
    }

    @Log
    public void core(int num) {
        System.out.println("----core---- " + num);
    }

    public void work(int num) {
        System.out.println("----work---- " + num);
    }

    public void testLog(){
//        TestLogStarterService.core(1);
//        TestLogStarterService.test(2);
//        TestLogStarterService.work(3);
    }

}
