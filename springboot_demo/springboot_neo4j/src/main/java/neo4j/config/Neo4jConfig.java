package neo4j.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by Johnny on 2018/3/17.
 * extends Neo4jConfiguration 这个已经废弃，一个 EnableNeo4jRepositories 注解即可
 */
@Configuration
@EnableNeo4jRepositories("neo4j.repository")
@EnableTransactionManagement
public class Neo4jConfig {
    @Bean
    public org.neo4j.ogm.config.Configuration getConfiguration() {
        // 无用配置,自动扫描读取 application.properties
        return new org.neo4j.ogm.config.Configuration.Builder().uri("bolt://localhost:7687").build();
    }

//    @Bean
//    @Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
//    public Session getSession() throws Exception {
//        return super.getSession();
//    }

    /**
     * 如果不指定节点映射的java bean路径，保存时会报如下警告，导致无法将节点插入Neo4j中
     * ... is not an instance of a persistable class
     */
//    @Bean
//    public SessionFactory getSessionFactory() {
//        return new SessionFactory(getConfiguration(), "neo4j.domain");
//    }
}
