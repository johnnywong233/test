package neo4j.config;

import jakarta.annotation.Resource;
import org.neo4j.driver.Session;
import org.springframework.stereotype.Service;

/**
 * Created by Johnny on 2018/3/17.
 */
@Service
public class Neo4jDBCleaner {
    @Resource
    private Session session;

    public void cleanDb() {
        session.close();
    }
}
