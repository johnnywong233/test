package neo4j.service;

import lombok.extern.slf4j.Slf4j;
import neo4j.domain.Actor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by Johnny on 2018/3/17.
 */
@Slf4j
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
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