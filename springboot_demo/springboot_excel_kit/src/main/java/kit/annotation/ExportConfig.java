package kit.annotation;

import org.apache.poi.hssf.util.HSSFColor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ExportConfig {

    String value() default "field";

    short width() default -1;

    /**
     * 将单元格值进行转换后再导出：<br/>
     * 目前支持以下几种场景：<br/>
     * 1. 固定的数值转换为字符串值（如：1代表男，2代表女）<br/>
     * <b>表达式:</b> "s:1=男,2=女"<br/>
     * <p>
     * 2. 数值对应的值需要查询数据库才能进行映射(实现ExportConvert接口)<br/>
     * <b>表达式:</b> "c:ExportConvert实现类"
     *
     * @return 默认不启用
     */
    String convert() default "";

    /**
     * @return 当前单元格的字体颜色 (默认 HSSFColor.BLACK.index)
     */
    short color() default HSSFColor.BLACK.index;

    /**
     * 将单元格的值替换为当前配置的值：<br/>
     * 应用场景: <br/>
     * 密码字段导出为："******"
     *
     * @return 默认true
     */
    String replace() default "";

    /**
     * 设置单元格数据验证（下拉框）
     * <b>表达式:</b> "c:ExportRange实现类"
     *
     * @return 默认不启用
     */
    String range() default "";
}
