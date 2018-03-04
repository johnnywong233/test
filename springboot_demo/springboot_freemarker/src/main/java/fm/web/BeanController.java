package fm.web;

import fm.domain.City;
import fm.domain.User;
import fm.mapper.ds1.User1Mapper;
import fm.mapper.ds2.User2Mapper;
import fm.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Author: Johnny
 * Date: 2017/7/15
 * Time:30
 */
//针对FreeMarker的请求不能使用@RestController
@Controller
public class BeanController {
    @Autowired
    private CityService cityService;

    //出现这样的报错是因为request的URL和return的模版一样：
    // javax.servlet.ServletException: Circular view path [city]: would dispatch back to the current handler URL [/city] again. Check your ViewResolver setup!
    // (Hint: This may be the result of an unspecified view, due to default view name generation.
    //解决方法就是设置不同的
    @RequestMapping(value = "/city/get", method = RequestMethod.GET)
    public String findById(Model model, @RequestParam(value = "id") Long id) {
        model.addAttribute("city", cityService.findCityById(id));
        return "city";
    }

    @RequestMapping(value = "/city/get1", method = RequestMethod.GET)
    public String findByName(Model model, @RequestParam(value = "cityName") String name) {
        model.addAttribute("city", cityService.findCityByName(name));
        return "city";
    }

    @RequestMapping(value = "/cities", method = RequestMethod.GET)
    public String findAllCity(Model model) {
        List<City> cityList = cityService.findAllCity();
        model.addAttribute("cityList", cityList);
        return "cityList";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "Spring rest doc testing returning hello world";
    }

    @Autowired
    private User1Mapper user1Mapper;

    @Autowired
    private User2Mapper user2Mapper;

    @RequestMapping("/getUsers")
    @ResponseBody
    public List<User> getUsers() {
        return user1Mapper.getAll();
    }

    @RequestMapping("/getUser/{id}")
    @ResponseBody
    public User getUser(@PathVariable("id") Long id) {
        return user2Mapper.getOne(id);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public void save(User user) {
        user2Mapper.insert(user);
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public void update(User user) {
        user2Mapper.update(user);
    }

    @RequestMapping(value = "/delete/{id}")
    @ResponseBody
    public void deleteUser(@PathVariable("id") Long id) {
        user1Mapper.delete(id);
    }

}