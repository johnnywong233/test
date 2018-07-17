package es;

import lombok.Data;

import java.util.List;

/**
 * Author: Johnny
 * Date: 2018/6/22
 * Time: 15:42
 */
@Data
public class ESResultObject {
    private long took;
    private boolean timedOut;
    private ESShard shards;
    private ESHitsObject hits;
    private ESAggsObject aggregations;

    @Data
    public static class ESAggsObject {
        private ESAggregationObject esAggregationObject;
    }

    @Data
    public static class ESAggregationObject {
        private List<ESBucket> buckets;
    }
}
