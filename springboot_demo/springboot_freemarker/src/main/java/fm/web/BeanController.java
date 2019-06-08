package fm.web;

import fm.domain.City;
import fm.domain.User;
import fm.mapper.ds1.User1Mapper;
import fm.mapper.ds2.User2Mapper;
import fm.service.CityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;

/**
 * 针对FreeMarker的请求不能使用@RestController
 *
 * @author Johnny
 * Date: 2017/7/15
 * Time:30
 */
@Controller
@Api(tags = "城市及其用户")
public class BeanController {
    @Resource
    private CityService cityService;

    //出现这样的报错是因为request的URL和return的模版一样：
    // javax.servlet.ServletException: Circular view path [city]: would dispatch back to the current handler URL [/city] again. Check your ViewResolver setup!
    // (Hint: This may be the result of an unspecified view, due to default view name generation.
    //解决方法就是设置不同的
    @ApiOperation(value = "get one city", notes = "one")
    @RequestMapping(value = "/city/get", method = RequestMethod.GET)
    public String findById(Model model, @RequestParam(value = "id") Long id) {
        model.addAttribute("city", cityService.findCityById(id));
        //此处有数据返回，但是页面 404，是 freemarker 的模版引擎解析失败
        return "city";
    }

    @RequestMapping(value = "/city/get1", method = RequestMethod.GET)
    public String findByName(Model model, @RequestParam(value = "cityName") String name) {
        model.addAttribute("city", cityService.findCityByName(name));
        return "city";
    }

    @ApiOperation(value = "get all cities", notes = "all")
    @RequestMapping(value = "/cities", method = RequestMethod.GET)
    public String findAllCity(Model model) {
        List<City> cityList = cityService.findAllCity();
        model.addAttribute("cityList", cityList);
        return "cityList";
    }

    @ApiOperation("根目录")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "Spring rest doc testing returning hello world";
    }

    @Resource
    private User1Mapper user1Mapper;

    @Resource
    private User2Mapper user2Mapper;

    @ApiOperation("所有用户")
    @RequestMapping(value = "/getUsers", method = RequestMethod.GET)
    @ResponseBody
    public List<User> getUsers(@ApiParam("查看第几页") @RequestParam int pageIndex,
                               @ApiParam("每页多少条") @RequestParam int pageSize
    ) {
        return user1Mapper.getAll();
    }

    @ApiOperation("一个用户")
    @RequestMapping(value = "/getUser/{id}", method = RequestMethod.GET)
    @ResponseBody
    public User getUser(@PathVariable("id") Long id) {
        return user2Mapper.getOne(id);
    }

    @ApiOperation("添加用户")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public void save(User user) {
        user2Mapper.insert(user);
    }

    @ApiOperation("更新用户")
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public void update(User user) {
        user2Mapper.update(user);
    }

    @ApiIgnore
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteUser(@PathVariable("id") Long id) {
        user1Mapper.delete(id);
    }

}