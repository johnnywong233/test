package com.johnny;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.h2.tools.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.Arrays;

/**
 * @author Johnny
 * @since 2019/5/31-20:57
 */
@SpringBootApplication
public class DroolsApplication {
    @Resource
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(DroolsApplication.class, args);
    }

    /**
     * 定义一个配置initMethod 和destroyMethod参数的方法将在Spring启动和关闭H2内存数据库时被调用。
     * -tcp 参数指示H2数据库可以通过一个TCP服务启动。我们指定在createTcpServer方法的第三和第四参数中使用指定的端口。
     * tcpAllowOthers 参数使得H2可以通过运行在同一主机或远程主机上的外部应用程序访问。
     */
    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server inMemoryH2DatabaseServer() throws SQLException {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9090");
    }

    @PostConstruct
    private void initDb() {
        System.out.printf("****** Creating table: %s, and Inserting test data ******%n", "person");
        String[] sqlStatements = {
                "drop table person if exists",
                "create table person(id serial,first_name varchar(255),last_name varchar(255))",
                "insert into person(first_name, last_name) values('awesome','johnny')",
                "insert into person(first_name, last_name) values('sweet','angel')"
        };
        Arrays.asList(sqlStatements).forEach(sql -> jdbcTemplate.execute(sql));

        // Query test data and print results
        System.out.printf("****** Fetching from table: %s ******%n", "person");
        jdbcTemplate.query("select id,first_name,last_name from person",
                (rs, i) -> {
                    System.out.printf("id:%s, first_name:%s,last_name:%s%n",
                            rs.getString("id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"));
                    return null;
                });
    }
}
