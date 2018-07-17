package es;

import lombok.Data;
import org.apache.commons.collections.CollectionUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.util.List;

/**
 * Author: Johnny
 * Date: 2018/6/22
 * Time: 15:20
 */
public class MapEsQuery {
    /**
     * QueryDTO list组合查询转换为ES CombineQuery
     */
    public static String getCombineQuery(List<QueryDTO> queryDTOs) {
        ESCombineQuery esCombineQuery = new ESCombineQuery();

        for (QueryDTO queryDTO : queryDTOs) {
            esCombineQuery.addQuery(ESCombineQuery.ESCombineType.FILTER, mapESQuery(queryDTO));
        }
        return esCombineQuery.getQuery();
    }

    /**
     * queryDTO转为ESQuery
     */
    private static String mapESQuery(QueryDTO queryDTO) {
        //模糊匹配
        if (queryDTO.getIsFuzzy() != null && queryDTO.getIsFuzzy()) {
            return ESQuery.getFuzzyQuery(queryDTO.getKey(), String.format(".*%s.*", queryDTO.getValue()));
        }

        //范围匹配
        if (queryDTO.getIsRange() != null && queryDTO.getIsRange()) {
            return ESQuery.getRangeQuery(queryDTO.getKey(), queryDTO.getGtValue(), queryDTO.getLtValue());
        }

        if (queryDTO.getIsInList() != null && queryDTO.getIsInList()) {
            return ESQuery.getMatchQuery(queryDTO.getKey(),
                    "\"" + String.join(",", queryDTO.getListValues()) + "\""
            );
        }
        return ESQuery.getMatchQuery(queryDTO.getKey(), queryDTO.getValue());
    }

    /**
     * 根据条件查询
     */
    public static SearchSourceBuilder getCombineBuilderQuery(List<QueryDTO> queryDTOs) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        if (CollectionUtils.isNotEmpty(queryDTOs)) {

            for (QueryDTO queryDTO : queryDTOs) {
                if (queryDTO.getIsNotInList() != null && queryDTO.getIsNotInList()) {
                    queryBuilder.mustNot(mapESQueryBuilder(queryDTO));
                } else {
                    queryBuilder.filter(mapESQueryBuilder(queryDTO));
                }
            }

        }
        searchSourceBuilder.query(queryBuilder);
        return searchSourceBuilder;
    }

    /**
     * 排序查询
     */
    public static SearchSourceBuilder getSortQuery(SearchSourceBuilder searchSourceBuilder, List<ESSorter> esSorters) {
        if (CollectionUtils.isNotEmpty(esSorters)) {
            esSorters.forEach(sort -> searchSourceBuilder.sort(sort.getField(), sort.isAsc() ? SortOrder.ASC : SortOrder.DESC)
            );
        }
        return searchSourceBuilder;
    }

    /**
     * 分页查询
     */
    public static SearchSourceBuilder getPageQuery(SearchSourceBuilder searchSourceBuilder, ESPager esPager) {
        if (esPager != null) {
            searchSourceBuilder.from(esPager.getFrom()).size(esPager.getSize());
        }
        return searchSourceBuilder;
    }

    public static SearchSourceBuilder getAggregationsQuery(SearchSourceBuilder searchSourceBuilder, List<String> aggs) {
        if (CollectionUtils.isNotEmpty(aggs)) {
            aggs.forEach(agg ->
                    searchSourceBuilder.aggregation(AggregationBuilders.terms("agg_" + agg).field(agg))
            );
        }
        return searchSourceBuilder;
    }


    private static QueryBuilder mapESQueryBuilder(QueryDTO queryDTO) {
        //模糊匹配
        if (queryDTO.getIsFuzzy() != null && queryDTO.getIsFuzzy()) {
            return QueryBuilders.fuzzyQuery(queryDTO.getKey(), queryDTO.getValue());
        }

        //范围匹配
        if (queryDTO.getIsRange() != null && queryDTO.getIsRange()) {
            return QueryBuilders.rangeQuery(queryDTO.getKey()).gte(queryDTO.getLtValue()).lte(queryDTO.getGtValue());
        }

        if (queryDTO.getIsInList() != null && queryDTO.getIsInList()) {
            return QueryBuilders.termsQuery(queryDTO.getKey(), queryDTO.getListValues());
        }

        if (queryDTO.getIsNotInList() != null && queryDTO.getIsNotInList()) {
            return QueryBuilders.termsQuery(queryDTO.getKey(), queryDTO.getListValues());
        }

        return QueryBuilders.matchQuery(queryDTO.getKey(), queryDTO.getValue());
    }


    @Data
    public static class QueryDTO {
        private String key;
        private String value;
        private Boolean isFuzzy;
        private Boolean isRange;
        private Boolean isInList;
        private Boolean isNotInList;
        private String gtValue;
        private String ltValue;
        private List<String> listValues;
    }

}
