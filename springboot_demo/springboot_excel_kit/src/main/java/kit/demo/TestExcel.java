package kit.demo;

import jakarta.servlet.http.HttpServletResponse;
import kit.bo.ExcelKitBo;
import kit.util.ExcelKit;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Johnny on 2018/4/5.
 */
@Service
public class TestExcel {

    void testExcelKit(HttpServletResponse response) {
        List<ExcelKitBo> excelKitBoList = new ArrayList<>();

        ExcelKitBo excelKitBo = new ExcelKitBo();
        excelKitBo.setMobile("123456");
        excelKitBo.setName("johnny the awesome");
        excelKitBo.setSex("MALE");

        excelKitBoList.add(excelKitBo);

        ExcelKit.exportExcel(ExcelKitBo.class, response).toExcel(excelKitBoList, "测试数据");
    }
}
