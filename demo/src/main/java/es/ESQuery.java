package es;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Author: Johnny
 * Date: 2018/6/22
 * Time: 15:32
 */
public class ESQuery {
    /**
     * match
     */
    public static String getMatch(Map<String, String> kv) {
        String match = "";
        List<String> queryline = Lists.newArrayList();
        for (Map.Entry<String, String> item : kv.entrySet()) {
            queryline.add("\"" + item.getKey() + "\":" + item.getValue());
        }

        if (kv.isEmpty()) {
            match = "\"match_all\": {}";
        } else {
            match = "    \"match\": {\n" + String.join(",\n", queryline) + "    \n}\n";
        }

        return match;
    }

    /**
     * fuzzy query
     */
    public static String getFuzzyQuery(String field, String regex) {
        return String.format("{\"regexp\": {\"%s\": \"%s\"" + "}}", field,
                regex);
    }


    public static String getRangeQuery(String field, String gt, String lt) {
        return String.format("{\"range\":{ \"%s\": {\"gt\": %s,\"lt\": %s} } }", field, gt, lt);
    }

    public static String getMatchQuery(String field, String value) {
        return String.format("{\"match\":{ \"%s\": %s }}", field, value);
    }

    /**
     * sorter string
     */
    public static String getSorter(List<ESSorter> sorters) {
        String sort = "";
        if (sorters != null && !sorters.isEmpty()) {

            sort = "\"sort\":[" + String.join(",\n", sorters.stream()
                    .map(k -> "{ \"" + k.getField() + "\":   { \"order\": \"" + (k.isAsc() ? "asc" : "desc") + "\" }}")
                    .collect(Collectors.toList())) + "]";
        }
        return sort;

    }

    /**
     * pager string
     */
    public static String getPager(ESPager pager) throws Exception {
        String page = "";
        if (pager != null) {
            if (pager.getFrom() < 0 || pager.getSize() == 0) {
                throw new Exception("分页参数from/size");
            }
            page = "\"from\":" + pager.getFrom() + ",\n \"size\":" + pager.getSize() + "\n";
        }
        return page;
    }

    /**
     * aggregation request
     */
    public static String getAggregations(List<String> aggs) {
        if (aggs == null || aggs.isEmpty()) {
            return null;
        }
        return "\"aggs\":{" + String.join(",", aggs.stream().map(k -> String.format("\"agg_%s\":{\"terms\":{\"field\": \"%s\"}}", k, k)).collect(Collectors.toList())) + "}";
    }
}
