package saas;

import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import saas.db.TenantRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;

/**
 * Author: Johnny
 * Date: 2017/8/17
 * Time: 0:04
 */
@Configuration
public class DbConfig {

    @Resource
    private DataSourceProperties properties;

    private DataSource defaultDataSource() {
        return DataSourceBuilder
                .create(this.properties.getClassLoader())
                .url(this.properties.getUrl())
                .username(this.properties.getUsername())
                .password(this.properties.getPassword()).build();
    }

    @Bean
    public DataSource dataSource() {
        TenantRoutingDataSource routingDataSource = new TenantRoutingDataSource();
        routingDataSource.setDefaultTargetDataSource(this.defaultDataSource());
        routingDataSource.setTargetDataSources(new HashMap<>());
        return routingDataSource;
    }
}
