package es;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import log.LogUtil;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.ResponseListener;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Author: Johnny
 * Date: 2018/6/22
 * Time: 16:09
 */
public class ESRestServiceImpl implements ESRestService {

    private static final String CFG_HOST = "host";
    private static final String CFG_PORT = "port";
    private RestClient restClient;

    /**
     * 获取rest client
     */
    @Override
    public RestClient getRestClient() {
        return restClient;
    }

    /**
     * 初始化
     */
    @PostConstruct
    public void init() {

//		MapConfig config = MapConfig.get("elasticsearch.properties");
//		if (config == null) {
//			LogUtil.error("elasticsearch.properties is not exists");
//		}else {
//
//			Map<String, String> cfgmap = config.asMap();
//			String host = cfgmap.get(CFG_HOST);
//			int port = Integer.parseInt(cfgmap.get(CFG_PORT));
//
//			restClient = RestClient.builder(new HttpHost(host, port, "http")).build();
//		}
    }

    @PreDestroy
    public void destroy() {
        if (restClient != null) {
            try {
                restClient.close();
                LogUtil.info("restClient is closed");
            } catch (IOException e) {
                LogUtil.error("restClient close exception", e);
            }
        }
    }

    @Override
    public boolean putSync(String index, String type, String id, HttpEntity entity) {
        Response indexResponse = null;
        try {
            indexResponse = restClient.performRequest("POST", String.format("/%s/%s/%s", index, type, id),
                    Collections.emptyMap(), entity);
        } catch (IOException e) {
            LogUtil.error("putSync exception", e);
        }
        return (indexResponse != null);
    }

    @Override
    public void putAsync(String index, String type, String id, HttpEntity entity) {

        restClient.performRequestAsync("PUT", String.format("/%s/%s/%s", index, type, id), new ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                System.out.println(response);
            }

            @Override
            public void onFailure(Exception exception) {
                System.out.println("failure in async scenrio");
            }
        });

    }

    /**
     * 根据字段获取
     */
    @Override
    public HttpEntity matchByFileds(String index, String type, Map<String, String> kv, List<ESSorter> sorters,
                                    ESPager... pager) throws Exception {
        String method = "POST";
        String endpoint = String.format("/%s/%s/_search", index, type);
        String match;
        String sort;
        String page = "";

        match = ESQuery.getMatch(kv);

        if (pager.length > 0) {
            page = ESQuery.getPager(pager[0]);
        }

        sort = ESQuery.getSorter(sorters);
        StringBuilder query = new StringBuilder();
        query.append("{\n\"query\": {\n");
        query.append(match);
        query.append("}\n");
        if (!Strings.isNullOrEmpty(sort)) {
            query.append(",");
            query.append(sort);
        }

        if (!Strings.isNullOrEmpty(page)) {
            query.append(",");
            query.append(page);
        }
        query.append("\n}");

        System.out.println(query.toString());
        HttpEntity entity = new NStringEntity(query.toString(), ContentType.APPLICATION_JSON);
        Response response = restClient.performRequest(method, endpoint, Collections.singletonMap("pretty", "true"),
                entity);

        return response.getEntity();
    }

    @Override
    public HttpEntity query(String index, String type, List<MapEsQuery.QueryDTO> querys, List<String> aggregations, List<ESSorter> sorters,
                            ESPager... pager) throws IOException {

//		StringBuilder query = new StringBuilder();
//		String sort =  ESQuery.getSorter(sorters);
//		String page =  "";
//
//		query.append("{");
//
//		query.append(MapESQuery.getCombineQuery(querys));
//
//
//		if(!Strings.isNullOrEmpty(sort)){
//			query.append(",");
//			query.append(sort);
//		}
//
//		if (pager.length > 0) {
//			page = ESQuery.getPager(pager[0]);
//		}
//
//		if(!Strings.isNullOrEmpty(page)){
//			query.append(",");
//			query.append(page);
//		}
//
//		if(aggregations!=null && !aggregations.isEmpty()){
//			query.append(",");
//			query.append(ESQuery.getAggregations(aggregations));
//		}
//
//		query.append("}");

        SearchSourceBuilder searchSourceBuilder = MapEsQuery.getCombineBuilderQuery(querys);
        MapEsQuery.getSortQuery(searchSourceBuilder, sorters);
        MapEsQuery.getPageQuery(searchSourceBuilder, pager[0]);
        MapEsQuery.getAggregationsQuery(searchSourceBuilder, aggregations);
        System.out.println(searchSourceBuilder.toString());
        LogUtil.info("ESRestService query", searchSourceBuilder.toString());
        HttpEntity entity = new NStringEntity(searchSourceBuilder.toString(), ContentType.APPLICATION_JSON);

        try {
            Response result = restClient.performRequest("POST", String.format("/%s/%s/_search", index, type),
                    Collections.singletonMap("pretty", "true"), entity);
            return result.getEntity();
        } catch (IOException e) {
            LogUtil.error("queryInquiryByEs exception", e);
        }
        return null;
    }


    @Override
    public HttpEntity fiter(String index, String type, Map<String, String> kv, ESPager... pager) throws IOException {
        String method = "POST";
        String endpoint = String.format("/%s/%s/_search", index, type);
        if (kv.isEmpty()) {
            return null;
        }

        List<String> queryline = Lists.newArrayList();
        for (Map.Entry<String, String> item : kv.entrySet()) {
            queryline.add("\"" + item.getKey() + "\":" + item.getValue());
        }

        String query = String.format("{\n" + "  \"bool\": {\n" + "  		\"query\": {\n"
                        + "    		\"must\": { \"match_all\": {} }\n" + "  		}\n" + "  	}\n" + "}",
                String.join(",\n", queryline));

        System.out.println(query);
        HttpEntity entity = new NStringEntity(query, ContentType.APPLICATION_JSON);
        Response response = restClient.performRequest(method, endpoint, Collections.singletonMap("pretty", "true"),
                entity);

        return response.getEntity();
    }

    /**
     * 创建index,设置映射
     */
    @Override
    public boolean createIndex(String index, String type, List<TwoTuple<String, IndexType>> indexTypes) {
        if (indexTypes == null || indexTypes.isEmpty()) {
            return false;
        }

        String properties = String
                .join(",\n",
                        indexTypes.stream()
                                .map(k -> mapPropertyIndex(k))
                                .collect(Collectors.toList()));

        Response indexResponse = null;
        try {
            String postStr = "{\"settings\":{\n" +
                    "\"analysis\":{\n" +
                    "\"analyzer\":{\n" +
                    "\"comma\":{\"type\":\"pattern\",\"pattern\":\",\"}\n}\n}\n}\n," +
                    "\"mappings\":{\n" +
                    "\"" + type + "\":{\n" +
                    "\"properties\":{\n" +
                    properties +
                    "\n}\n}\n}}";
            System.out.println(postStr);
            HttpEntity entity = new NStringEntity(postStr,
                    ContentType.APPLICATION_JSON);

            indexResponse = restClient.performRequest("PUT", String.format("/%s", index),
                    Collections.emptyMap(), entity);
        } catch (IOException e) {
            LogUtil.error("createIndex exception", e);
        }

        return (indexResponse != null);
    }

    /**
     * 字段映射
     */
    private String mapPropertyIndex(TwoTuple<String, IndexType> k) {
        StringBuilder result = new StringBuilder();

        result.append("\"" + k.first + "\":{");
        result.append("\"type\":\"" + k.second.getType() + "\"");
        if (k.second.getIndex() != null) {
            if ("not_analyzed".equalsIgnoreCase(k.second.getIndex())) {
                result.append(",\"index\":\"not_analyzed\"");
            } else {
                result.append(",\"analyzer\":\"" + k.second.getIndex() + "\"");
                result.append(",\"search_analyzer\":\"" + k.second.getIndex() + "\"");
            }
        }

        result.append("}");

        return result.toString();
    }

    /**
     * 设置逗号分词器
     */
    @Override
    public boolean setCommaAnalyzer(String index) {
        Response indexResponse = null;
        try {
            String postStr =
                    "{\"settings\":{\n" +
                            "\"analysis\":{\n" +
                            "\"analyzer\":{\n" +
                            "\"comma\":{\"type\":\"pattern\",\"pattern\":\",\"}\n}\n}\n}\n}";
            System.out.println(postStr);
            HttpEntity entity = new NStringEntity(postStr,
                    ContentType.APPLICATION_JSON);

            indexResponse = restClient.performRequest("PUT", String.format("/%s", index),
                    Collections.emptyMap(), entity);
        } catch (IOException e) {
            LogUtil.error("putSync exception", e);
        }

        return (indexResponse != null);
    }

    /**
     * 添加字段映射
     */
    @Override
    public boolean addIndexProperties(String index, String type, List<TwoTuple<String, IndexType>> indexTypes) {
        Response indexResponse = null;
        try {
            String properties = String
                    .join(",\n",
                            indexTypes.stream()
                                    .map(k -> mapPropertyIndex(k))
                                    .collect(Collectors.toList()));

            String postStr = "{\"properties\":{" + properties + "}}";
            System.out.println(postStr);
            HttpEntity entity = new NStringEntity(postStr,
                    ContentType.APPLICATION_JSON);

            indexResponse = restClient.performRequest("POST", String.format("/%s/%s/_mapping", index, type),
                    Collections.emptyMap(), entity);
        } catch (IOException e) {
            LogUtil.error("putSync exception", e);
        }

        return (indexResponse != null);
    }


}
