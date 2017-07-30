package petshop.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Locale;

/**
 * Author: Johnny
 * Date: 2017/7/9
 * Time: 13:13
 */
@Configuration
@EnableTransactionManagement
@EnableAsync
@EnableAspectJAutoProxy
@EnableScheduling
@Import({})
@ComponentScan({"petshop"})
public class AppConfiguration {
    private Logger logger = LoggerFactory.getLogger(AppConfiguration.class);

    public AppConfiguration() {
        logger.info("[Initialize application]");
        Locale.setDefault(Locale.US);
    }
}
