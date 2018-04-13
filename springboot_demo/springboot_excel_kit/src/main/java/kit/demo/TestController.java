package kit.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Johnny on 2018/4/5.
 */
@RestController
public class TestController {

    @Autowired
    private TestExcel testExcel;

    @RequestMapping(value = "test")
    public void test(HttpServletResponse response) {
        testExcel.testExcelKit(response);
    }

}
