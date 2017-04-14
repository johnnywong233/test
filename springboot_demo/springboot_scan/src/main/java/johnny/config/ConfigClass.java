package johnny.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * classpath路径：locations={"classpath:application-bean1.xml","classpath:application-bean2.xml"}
 */
@Configuration
@ImportResource(locations = {"classpath:application-bean.xml"})
//@EnableAutoConfiguration(exclude={HelloService1.class})//Not work, 没有include功能, exclude用于那些*AutoConfiguration.class(有待验证)
//@EnableAutoConfiguration(excludeName={"johnny.service.HelloService1"})//Not work
//@ImportResource(locations={"file:d:/test/application-bean1.xml"})//for those local xml bean files, better no use
public class ConfigClass {
}