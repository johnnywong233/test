package mybatis.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

@Slf4j
@Configuration
@EnableTransactionManagement
public class DruidDataSourceConfig implements EnvironmentAware {

    private Properties properties;

    @Override
    public void setEnvironment(@NonNull Environment env) {
        this.properties = Binder.get(env)
                .bind("spring.datasource.", Properties.class)
                .orElse(null);
    }

    @Bean
    public DataSource dataSource() {
        log.info("inject druid.");
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(properties.getProperty("url"));
        datasource.setDriverClassName(properties.getProperty("driver-class-name"));
        datasource.setUsername(properties.getProperty("username"));
        datasource.setPassword(properties.getProperty("password"));
        datasource.setInitialSize(Integer.parseInt(properties.getProperty("initial-size")));
        datasource.setMinIdle(Integer.parseInt(properties.getProperty("min-idle")));
        datasource.setMaxWait(Long.parseLong(properties.getProperty("max-wait")));
        datasource.setMaxActive(Integer.parseInt(properties.getProperty("max-active")));
        datasource.setMinEvictableIdleTimeMillis(Long.parseLong(properties.getProperty("min-evictable-idle-time-millis")));
        try {
            datasource.setFilters("stat, wall");
        } catch (SQLException e) {
            log.error("dataSource init fail", e);
        }
        return datasource;
    }
}
