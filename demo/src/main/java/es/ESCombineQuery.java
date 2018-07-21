package es;

/**
 * Author: Johnny
 * Date: 2018/6/22
 * Time: 15:26
 * es组合查询
 */
public class ESCombineQuery {
    private StringBuilder querys = null;
    private int querycount = 0;
    private boolean addEndTag = false;

    public ESCombineQuery() {
        querys = new StringBuilder("\"query\":{ \"bool\":{  ");
    }

    public ESCombineQuery addQuery(ESCombineType combineType, String query) {
        if (querycount > 0) {
            this.querys.append(",\n");
        }
        this.querys.append(String.format("\"%s\":%s", combineType.toString(), query));
        ++querycount;
        return this;
    }

    public String getQuery() {
        if (!addEndTag) {
            querys.append(" } } ");
            addEndTag = true;
        }
        return querys.toString();
    }

    public int getQueryCount() {
        return querycount;
    }

    enum ESCombineType {
        //
        MUST, MUST_NOT, SHOULD, FILTER
    }

}
