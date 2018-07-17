package es;

import lombok.Data;

/**
 * Author: Johnny
 * Date: 2018/6/22
 * Time: 15:40
 */
@Data
public class ESHitsObjectList<T> {
    private String index;
    private String type;
    private String id;
    private double score;
    private T source;
}
