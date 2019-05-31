package h2.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @author Johnny
 * @date 2019/5/31-21:57
 */
@SpringBootApplication
public class ClientH2Application {
    @Resource
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void initDb() {
        System.out.println("****** Inserting more sample data in the table: person ******");

        String[] sqlStatements = {
                "insert into person(first_name, last_name) values('fantastic','Jay')",
                "insert into person(first_name, last_name) values('terrific','himekami')"
        };
        Arrays.asList(sqlStatements).forEach(sql -> jdbcTemplate.execute(sql));

        // Fetch data using SELECT statement and print results
        System.out.println(String.format("****** Fetching from table: %s ******", "person"));
        jdbcTemplate.query("select id,first_name,last_name from person",
                (rs, i) -> {
                    System.out.println(String.format("id:%s,first_name:%s,last_name:%s",
                            rs.getString("id"),
                            rs.getString("first_name"),
                            rs.getString("last_name")));
                    return null;
                });
    }

    public static void main(String[] args) {
        // 更新URL
        System.setProperty("spring.datasource.url","jdbc:h2:tcp://localhost:9090/mem:mydb");
        SpringApplication.run(ClientH2Application.class, args);
    }
}
