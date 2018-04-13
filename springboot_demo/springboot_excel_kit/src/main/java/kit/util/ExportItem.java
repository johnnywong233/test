package kit.util;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ExportItem {

    /**
     * 属性名
     */
    private String field;
    /**
     * 显示名
     */
    private String display;
    /**
     * 宽度
     */
    private short width;
    private String convert;
    private short color;
    private String replace;
    /**
     * 数据有效性 下拉框
     */
    private String range;

    public ExportItem setField(String field) {
        this.field = field;
        return this;
    }
}
