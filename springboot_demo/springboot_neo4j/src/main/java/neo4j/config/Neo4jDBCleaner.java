package neo4j.config;

import org.neo4j.ogm.session.Session;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Johnny on 2018/3/17.
 */
@Service
public class Neo4jDBCleaner {
    @Resource
    private Session session;

    public void cleanDb() {
        session.purgeDatabase();
    }
}
