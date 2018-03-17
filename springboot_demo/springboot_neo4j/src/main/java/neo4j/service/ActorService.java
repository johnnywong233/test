package neo4j.service;

import com.google.common.collect.Lists;
import neo4j.domain.Actor;
import neo4j.domain.Movie;
import neo4j.domain.Seen;
import neo4j.repository.ActorRepository;
import neo4j.repository.MovieRepository;
import neo4j.repository.SeenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Johnny on 2018/3/17.
 */
@Service
public class ActorService {
    @Autowired
    private ActorRepository actorRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private SeenRepository seenRepository;

    @Transactional
    public void initData() {
        /*
          初始化用户
         */
        Actor actor1 = new Actor("John Johnson");
        Actor actor2 = new Actor("Kate Smith");
        Actor actor3 = new Actor("Jack Jeffries");
        /*
          为用户John添加朋友关系
         */
        actor1.setFriends(Lists.newArrayList(actor2, actor3));
        /*
          初始化电影
         */
        Movie movie1 = new Movie("Fargo");
        Movie movie2 = new Movie("Alien");
        Movie movie3 = new Movie("Heat");
        /*
          初始化HAS_SEEN关系
         */
        Seen hasSeen1 = new Seen(5, actor1, movie1);
        Seen hasSeen2 = new Seen(3, actor2, movie3);
        Seen hasSeen3 = new Seen(6, actor2, movie2);
        Seen hasSeen4 = new Seen(4, actor3, movie1);
        Seen hasSeen5 = new Seen(5, actor3, movie2);
        /*
          如果不加@Transactional，下面每个save都会单独开启事物
         */
        actorRepository.save(Lists.newArrayList(actor1, actor2, actor3));
        movieRepository.save(Lists.newArrayList(movie1, movie2, movie3));
        seenRepository.save(Lists.newArrayList(hasSeen1, hasSeen2, hasSeen3, hasSeen4, hasSeen5));
    }

    @Transactional
    public Actor getActorByName(String name) {
        return actorRepository.getActorByName(name);
    }
}
