package demo.demo2;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;

//@Service//效果等同于如下几种
//    @Component
//    @Repository
@Controller
public class UseFunctionService {
    @Resource
    private FunctionService functionService;

    String sayHello(String word) {
        return functionService.sayHello(word);
    }
}
