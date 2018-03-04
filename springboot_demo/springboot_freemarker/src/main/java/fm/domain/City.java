package fm.domain;

import lombok.Data;

/**
 * Author: Johnny
 * Date: 2017/7/15
 * Time: 23:32
 */
@Data
public class City {
    /**
     * 城市编号
     */
    private Long id;

    /**
     * 省份编号
     */
    private Long provinceId;

    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 描述
     */
    private String description;
}