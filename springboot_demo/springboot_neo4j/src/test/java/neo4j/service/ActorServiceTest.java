package neo4j.service;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import neo4j.domain.Actor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Created by Johnny on 2018/3/17.
 */
@Slf4j
@SpringBootTest
public class ActorServiceTest {
    @Resource
    private ActorService actorService;

    /**
     * 因为是通过http连接到Neo4j数据库的，所以要预先启动Neo4j：neo4j console
     */
    @Test
    public void testInitData() {
        actorService.initData();
    }

    @Test
    public void testGetUserByName() {
        Actor actor = actorService.getActorByName("John Johnson");
        log.info("actor:{}", actor);
    }
}