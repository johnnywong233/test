package es.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * Author: Johnny
 * Date: 2017/9/24
 * Time: 17:30
 */
@Configuration
@EnableElasticsearchRepositories(basePackages = "es.repository")
public class EsConfig {
    @Value("${elasticsearch.host}")
    private String esHost;

    @Value("${elasticsearch.port}")
    private int esPort;

    @Bean
    public RestHighLevelClient client() {
        // https://www.elastic.co/guide/en/elasticsearch/guide/current/_transport_client_versus_node_client.html
        // https://www.elastic.co/guide/en/elasticsearch/client/java-rest/7.15/_changing_the_client_8217_s_initialization_code.html
        return new RestHighLevelClient(RestClient.builder(new HttpHost(esHost, esPort, "http")));
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() throws Exception {
        return new ElasticsearchTemplate(client());
    }

    //Embedded Elasticsearch Server
    /*@Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        return new ElasticsearchTemplate(nodeBuilder().local(true).node().client());
    }*/

}
