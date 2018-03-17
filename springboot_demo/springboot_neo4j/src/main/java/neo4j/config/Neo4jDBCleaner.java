package neo4j.config;

import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Johnny on 2018/3/17.
 */
@Service
public class Neo4jDBCleaner {
    @Autowired
    Session session;

    public void cleanDb() {
        session.purgeDatabase();
    }
}
