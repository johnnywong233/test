package paging.page;

import com.google.common.collect.Lists;
import lombok.Data;
import org.crazycake.utils.CamelNameUtils;
import org.joda.time.DateTime;
import org.mybatis.pagination.dto.datatables.PagingCriteria;
import org.mybatis.pagination.dto.datatables.SearchField;
import org.mybatis.pagination.dto.datatables.SortField;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Johnny on 2018/3/27.
 */
@Data
public class SearchCriteria {
    private final DateTime current = new DateTime();

    /**
     * default num
     */
    private int count = 10;

    private int page = 1;

    private String startDate = (new DateTime(current.getYear(), current.getMonthOfYear(), current.getDayOfMonth(), 0, 0, 0)).toString("yyy-MM-dd HH:mm:ss");

    private String endDate = current.toString("yyy-MM-dd HH:mm:ss");

    private Map<String, String> sortFields;

    private Map<String, String> searchFields;

    public PagingCriteria convertToPagingCriteria() {
        List<SearchField> searchFieldsList = Lists.newArrayListWithCapacity(0);
        List<SortField> sortFieldsList = Lists.newArrayListWithCapacity(0);
        if (null != this.sortFields) {
            for (Map.Entry<String, String> entry : this.sortFields.entrySet()) {
                sortFieldsList.add(new SortField(CamelNameUtils.camel2underscore(entry.getKey()), entry.getValue()));
            }
        }
        if (null != this.searchFields) {
            for (Map.Entry<String, String> entry : this.searchFields.entrySet()) {
                searchFieldsList.add(new SearchField(CamelNameUtils.camel2underscore(entry.getKey()), false, false, entry.getValue()));
            }
        }

        return PagingCriteria.createCriteriaWithAllParamter((this.page - 1) * this.count,
                this.count, this.page, sortFieldsList, searchFieldsList);
    }

    /**
     * 处理searchCriteria,当searchFields()中包含dateType=(today, last_***_minutes), 或startDate, 或endDate字段时
     */
    public void parseSearchCriteria() {

        if (this.getSearchFields() != null) {
            this.getSearchFields().remove("undefined");

            if (this.getSearchFields().containsKey("dateType")) {
                String dateType = this.getSearchFields().get("dateType");
                this.getSearchFields().remove("dateType");

                boolean check = null != dateType && ("custom".equals(dateType) || ("all".equals(dateType) ||
                        ("last".equals(dateType.split("_")[0]) && "minutes".equals(dateType.split("_")[2]))));
                if (check) {
                    switch (dateType.split("_")[0]) {
                        case "custom":
                            if (this.getSearchFields().containsKey("startDate")) {
                                this.setStartDate(this.getSearchFields().get("startDate"));
                            }
                            if (this.getSearchFields().containsKey("endDate")) {
                                this.setEndDate(this.getSearchFields().get("endDate"));
                            }
                            break;
                        case "all":
                            this.setStartDate("1970-01-01 00:00:00");
                            this.setEndDate("9999-12-31 23:59:59");
                            break;
                        case "last":
                            int lastMinutes = Integer.parseInt(dateType.split("_")[1]);
                            this.setStartDate(current.minusMinutes(lastMinutes).toString("yyy-MM-dd HH:mm:ss"));
                            this.setEndDate(current.toString("yyy-MM-dd HH:mm:ss"));
                            break;
                        default:
                            break;
                    }
                }

                //如果搜索字段中还有startDate或者endDate则直接移除，否则会被带入搜索条件
                this.getSearchFields().remove("startDate");
                this.getSearchFields().remove("endDate");
            }
        }
    }

    /**
     * 只处理数据库表中的字段,remove在searchFields和sortFields中的其余字段
     * criteria.removeUnusedColumn(Article.class);
     */
    @SuppressWarnings("rawtypes")
    public void removeUnusedColumn(Class c) {
        if (this.getSearchFields() != null) {
            this.setSearchFields(removeColumn(this.getSearchFields(), c));
        }
        if (this.getSortFields() != null) {
            this.setSortFields(removeColumn(this.getSortFields(), c));
        }
    }

    @SuppressWarnings("rawtypes")
    private Map<String, String> removeColumn(Map<String, String> handleFields, Class c) {
        try {
            Field[] fields = c.getDeclaredFields();
            ArrayList<String> columnList = new ArrayList<>();
            for (Field field : fields) {
                columnList.add(field.getName());
            }
            for (Map.Entry<String, String> entry : handleFields.entrySet()) {
                if (!columnList.contains(((Map.Entry) entry).getKey())) {
                    handleFields.remove(((Map.Entry) entry).getKey());
                }
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return handleFields;
    }
}
