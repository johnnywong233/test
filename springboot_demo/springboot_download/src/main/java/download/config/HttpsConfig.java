package download.config;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.Ssl;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
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
     */
    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return container -> {
            Ssl ssl = new Ssl();
            ssl.setKeyStore(jksName);
            ssl.setKeyStorePassword(pass);
            container.setSsl(ssl);
            container.setPort(redirectPort);
        };
    }

    @Bean
    public EmbeddedServletContainerFactory servletContainerFactory() {
        TomcatEmbeddedServletContainerFactory factory =
                new TomcatEmbeddedServletContainerFactory() {
                    @Override
                    protected void postProcessContext(Context context) {
                        //add SecurityConstraint for different redirect strategy from different URL
                        SecurityConstraint securityConstraint = new SecurityConstraint();
                        securityConstraint.setUserConstraint("CONFIDENTIAL");
                        SecurityCollection collection = new SecurityCollection();
                        collection.addPattern("/*");
                        securityConstraint.addCollection(collection);
                        context.addConstraint(securityConstraint);
                    }
                };
        factory.addAdditionalTomcatConnectors(createHttpConnector());
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
