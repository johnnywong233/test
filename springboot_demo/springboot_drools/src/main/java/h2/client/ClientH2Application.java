package h2.client;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;

/**
 * @author Johnny
 * @since 2019/5/31-21:57
 */
@Slf4j
@SpringBootApplication
public class ClientH2Application {
    @Resource
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        System.setProperty("spring.datasource.url", "jdbc:h2:tcp://localhost:9090/mem:mydb");
        SpringApplication.run(ClientH2Application.class, args);
    }

    @PostConstruct
    private void initDb() {
        log.info("****** Inserting more sample data in the table: person ******");

        String[] sqlStatements = {
                "insert into person(first_name, last_name) values('fantastic','Jay')",
                "insert into person(first_name, last_name) values('terrific','himekami')"
        };
        Arrays.asList(sqlStatements).forEach(sql -> jdbcTemplate.execute(sql));

        // Fetch data using SELECT statement and print results
        log.info("****** Fetching from table: {} ******%n", "person");
        jdbcTemplate.query("select id,first_name,last_name from person",
                (rs, i) -> {
                    log.info("id:{},first_name:{},last_name:{}\n",
                            rs.getString("id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"));
                    return null;
                });
    }
}
