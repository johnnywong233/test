package petshop.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import petshop.domain.Pet;

import javax.sql.DataSource;

/**
 * Author: Johnny
 * Date: 2017/7/10
 * Time: 19:33
 */
@Configuration
@EnableJpaRepositories(basePackageClasses = Pet.class,
        entityManagerFactoryRef = "petEntityManagerFactory")
public class DatasourceConfiguration {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Bean
    public LocalContainerEntityManagerFactoryBean orderEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(petDataSource())
                .packages(Pet.class)
                .persistenceUnit("orders")
                .build();
    }

    private DataSource petDataSource() {
        org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
        String userName = "postgres";
        String jdbcUrl = "jdbc:postgresql://localhost:5432/petshop";
        String driverClass = "org.postgresql.Driver";
        String dbPassword = "1Qaz";
        dataSource.setUsername(userName);
        dataSource.setUrl(jdbcUrl);
        dataSource.setDriverClassName(driverClass);
        dataSource.setPassword(dbPassword);
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(true);
        dataSource.setValidationQuery("SELECT 1");
        dataSource.setValidationInterval(5000);
        return dataSource;
    }

}
