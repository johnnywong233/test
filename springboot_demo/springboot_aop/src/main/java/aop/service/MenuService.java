package aop.service;

import aop.config.ApplicationContextHolder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Author: Johnny
 * Date: 2017/10/7
 * Time: 18:15
 */
@Service
public class MenuService {
    @Cacheable(cacheNames = {"menu"})
    public List<String> getMenuList(){
        System.out.println("mock: get from db");
        return Arrays.asList("article","comment","admin");
    }

    public List<String> getRecommends(){
        MenuService proxy = ApplicationContextHolder.getContext().getBean(MenuService.class);
        return proxy.getMenuList();
    }
}
