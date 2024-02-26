package demo1.config;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.Ssl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Author: Johnny
 * Date: 2017/7/8
 * Time: 10:18
 */
@Configuration
public class WebConfig {
    @Bean
    public TomcatServletWebServerFactory servletContainerFactory() {
        TomcatServletWebServerFactory factory =
                new TomcatServletWebServerFactory() {
                    @Override
                    protected void postProcessContext(Context context) {
                        // SecurityConstraint必须存在，可以通过其为不同的URL设置不同的重定向策略。
                        SecurityConstraint securityConstraint = new SecurityConstraint();
                        securityConstraint.setUserConstraint("CONFIDENTIAL");
                        SecurityCollection collection = new SecurityCollection();
                        collection.addPattern("/*");
                        securityConstraint.addCollection(collection);
                        context.addConstraint(securityConstraint);
                    }
                };
        factory.addAdditionalTomcatConnectors(createHttpConnector());
        Ssl ssl = new Ssl();
        // Server.jks中包含服务器私钥和证书
        ssl.setKeyStore("test.jks");
        ssl.setKeyStorePassword("123456");
        factory.setSsl(ssl);
        factory.setPort(8443);
        return factory;
    }

    private Connector createHttpConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        connector.setSecure(false);
        connector.setPort(8080);
        connector.setRedirectPort(8443);
        return connector;
    }
}
