package ehcache.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.util.Objects;

/**
 * Author: Johnny
 * Date: 2017/4/17
 * Time: 23:39
 * 缓存配置.
 */
@Slf4j
@Configuration
@EnableCaching
public class CacheConfiguration {

    //ehcache 主要的管理器
    @Bean
    public EhCacheCacheManager ehCacheCacheManager(EhCacheManagerFactoryBean bean) {
        log.info("CacheConfiguration.ehCacheCacheManager()");
        return new EhCacheCacheManager(Objects.requireNonNull(bean.getObject()));
    }

    /**
     * 据shared与否的设置, Spring分别通过CacheManager.create(), 或new CacheManager()方式来创建一个ehcache基地.
     * 也说是说通过这个来设置cache的基地是这里的Spring独用,还是跟别的(如hibernate的Ehcache共享)
     */
    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
        log.info("CacheConfiguration.ehCacheManagerFactoryBean()");
        EhCacheManagerFactoryBean bean = new EhCacheManagerFactoryBean();
        bean.setConfigLocation(new ClassPathResource("conf/ehcache.xml"));
        bean.setShared(true);
        return bean;
    }
}
