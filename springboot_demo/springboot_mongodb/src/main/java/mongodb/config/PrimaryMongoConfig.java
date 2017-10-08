package mongodb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Author: Johnny
 * Date: 2017/9/15
 * Time: 23:08
 */
@Configuration
@EnableMongoRepositories(basePackages = "mongodb",
        mongoTemplateRef = PrimaryMongoConfig.MONGO_TEMPLATE)
public class PrimaryMongoConfig {
    static final String MONGO_TEMPLATE = "primaryMongoTemplate";
}
