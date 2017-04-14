package demo1.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

	//不写方法的话,HTTP所有 METHOD都支持
    @RequestMapping("/")
    public String index() {
        return "Index Page";
    }
    
	//仅支持GET
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String test() {
        return "Get Page";
    }

    //支持GET和POST
    @RequestMapping(value = "/hello", params ="test", method = {RequestMethod.POST, RequestMethod.GET}) //wrong,GET和POST都错
//    @RequestMapping(value = "/hello", method = {RequestMethod.POST, RequestMethod.GET}) //right
    public String hello() {
        return "Hello World!";
    }

}
