package download.config;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.Ssl;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Author: Johnny
 * Date: 2017/7/13
 * Time: 15:13
 */
@Configuration
public class HttpsConfig {

    @Value("${jks.file.name}")
    private String jksName;
    @Value("${jks.file.password}")
    private String pass;
    @Value("${http.port}")
    private Integer port;
    @Value("${https.port}")
    private Integer redirectPort;
    @Value("${http.schema}")
    private String schema;

    /**
     * below code for https enhancement
     * <a href="https://stackoverflow.com/questions/49406779/embeddedservletcontainercustomizer-in-spring-boot-2-0">...</a>
     * EmbeddedServletContainerCustomizer -> ConfigurableServletWebServerFactory
     */
    @Bean
    public ConfigurableServletWebServerFactory containerCustomizer() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory() {
                    @Override
                    protected void postProcessContext(Context context) {
                        // add SecurityConstraint for different redirect strategy from different URL
                        SecurityCollection collection = new SecurityCollection();
                        collection.addPattern("/*");

                        SecurityConstraint constraint = new SecurityConstraint();
                        constraint.setUserConstraint("CONFIDENTIAL");
                        constraint.addCollection(collection);
                        context.addConstraint(constraint);
                    }
                };
        factory.addAdditionalTomcatConnectors(this.createHttpConnector());

        Ssl ssl = new Ssl();
        ssl.setKeyStore(jksName);
        ssl.setKeyStorePassword(pass);

        factory.setSsl(ssl);
        factory.setPort(redirectPort);

        return factory;
    }

    private Connector createHttpConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme(schema);
        connector.setSecure(false);
        connector.setPort(port);
        connector.setRedirectPort(redirectPort);
        return connector;
    }
}
