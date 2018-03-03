package entity;

import lombok.Data;

/**
 * Created by Johnny on 2018/3/3.
 */
@Data
public class ReportFieldConfig {

    /**
     * 报表字段标识
     */
    private String name;

    /**
     * 报表字段标题
     */
    private String title;

    /**
     * 报表字段逻辑脚本
     */
    private String script;
}