package es;

import java.util.List;

/**
 * Author: Johnny
 * Date: 2018/6/22
 * Time: 15:40
 */
public class ESHitsObject<T> {
    private long total;
    private double maxScore;
    private List<ESHitsObjectList<T>> hits;

}
