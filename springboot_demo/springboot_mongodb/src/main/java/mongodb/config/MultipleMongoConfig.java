package mongodb.config;

import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import javax.annotation.Resource;

/**
 * Author: Johnny
 * Date: 2017/9/15
 * Time: 23:10
 */
@Configuration
public class MultipleMongoConfig {
    @Resource
    private MultipleMongoProperties mongoProperties;

    //NOTICE: care for IDEA warning(Field injection is not recommended) like the above, remove it, and add below configuration to get avoid of
    // "java.lang.Error: Unresolved compilation problem:
    //The method getSecondary() is undefined for the type MultipleMongoProperties"
    //但是成功执行之后，把下面的注释掉，又不报错了？IDEA的启动加载有缓存？？？
//    @Autowired
//    public void setMongoProperties(@SuppressWarnings("SpringJavaAutowiringInspection") MultipleMongoProperties mongoProperties) {
//        this.mongoProperties = mongoProperties;
//    }

    @Primary
    @Bean(name = PrimaryMongoConfig.MONGO_TEMPLATE)
    public MongoTemplate primaryMongoTemplate() {
        return new MongoTemplate(primaryFactory(this.mongoProperties.getPrimary()));
    }

    @Bean
    @Qualifier(SecondaryMongoConfig.MONGO_TEMPLATE)
    public MongoTemplate secondaryMongoTemplate() {
        return new MongoTemplate(secondaryFactory(this.mongoProperties.getSecondary()));
    }

    @Bean
    @Primary
    public MongoDbFactory primaryFactory(MongoProperties mongo) {
        return new SimpleMongoDbFactory(new MongoClient(mongo.getHost(), mongo.getPort()), mongo.getDatabase());
    }

    @Bean
    public MongoDbFactory secondaryFactory(MongoProperties mongo) {
        return new SimpleMongoDbFactory(new MongoClient(mongo.getHost(), mongo.getPort()), mongo.getDatabase());
    }
}
