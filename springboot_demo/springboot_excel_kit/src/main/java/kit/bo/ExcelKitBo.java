package kit.bo;

import kit.annotation.ExportConfig;
import lombok.Data;

/**
 * Created by Johnny on 2018/4/5.
 */
@Data
public class ExcelKitBo {
    @ExportConfig("姓名")
    private String name;

    @ExportConfig("手机")
    private String mobile;

    @ExportConfig("性别")
    private String sex;

}
