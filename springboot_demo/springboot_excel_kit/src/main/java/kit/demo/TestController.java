package kit.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Johnny on 2018/4/5.
 */
@RestController
public class TestController {

    @Resource
    private TestExcel testExcel;

    @RequestMapping(value = "test")
    public void test(HttpServletResponse response) {
        testExcel.testExcelKit(response);
    }

}
