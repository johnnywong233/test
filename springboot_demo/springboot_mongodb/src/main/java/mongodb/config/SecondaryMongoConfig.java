package mongodb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Author: Johnny
 * Date: 2017/9/15
 * Time: 23:09
 */
@Configuration
@EnableMongoRepositories(basePackages = "mongodb",
        mongoTemplateRef = SecondaryMongoConfig.MONGO_TEMPLATE)
public class SecondaryMongoConfig {
    static final String MONGO_TEMPLATE = "secondaryMongoTemplate";
}
