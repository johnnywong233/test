package es;

import org.apache.http.HttpEntity;
import org.elasticsearch.client.RestClient;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Author: Johnny
 * Date: 2018/6/22
 * Time: 16:07
 */
public interface ESRestService {
    RestClient getRestClient();

    /**
     * 同步方式向ES集群写入数据
     */
    boolean putSync(String index, String type, String id, HttpEntity entity);

    /**
     * 异步的方式向ES写入数据
     */
    void putAsync(String index, String type, String id, HttpEntity entity);

    /**
     * 根据kv获取
     */
    HttpEntity matchByFields(String index, String type, Map<String, String> kv, List<ESSorter> sorters, ESPager... pager) throws Exception;

    HttpEntity filter(String index, String type, Map<String, String> kv, ESPager... pager) throws IOException;

    /**
     * 初始化索引字段类型及分词
     */
    boolean createIndex(String index, String type, List<TwoTuple<String, IndexType>> indexTypes);

    HttpEntity query(String index, String type, List<MapEsQuery.QueryDTO> querys, List<String> aggregations, List<ESSorter> sorters,
                     ESPager... pager) throws IOException;

    /**
     * 设置逗号
     */
    boolean setCommaAnalyzer(String index);

    /**
     * 添加字段映射
     */
    boolean addIndexProperties(String index, String type, List<TwoTuple<String, IndexType>> indexTypes);

    class TwoTuple<A, B> {
        public final A first;
        public final B second;

        public TwoTuple(A a, B b) {
            this.first = a;
            this.second = b;
        }
    }
}
