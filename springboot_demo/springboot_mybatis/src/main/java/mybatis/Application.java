package mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

//take care
@MapperScan("mybatis.model.mapper")
@SpringBootApplication
@ServletComponentScan
public class Application extends SpringBootServletInitializer {
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.ofMegabytes(5));
        factory.setMaxRequestSize(DataSize.ofMegabytes(5));
        return factory.createMultipartConfig();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

//	@Bean
//	public DataSource dataSource() {
//		DruidDataSource dataSource = new DruidDataSource();
//		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//		dataSource.setUrl("jdbc:mysql://192.168.157.131:3306/springboot");
//		dataSource.setUsername("code");
//		dataSource.setMaxActive(30);
//		dataSource.setPassword("123456");
//		DatabaseDriver databaseDriver = DatabaseDriver.fromJdbcUrl("jdbc:mysql://192.168.157.131:3306/springboot");
//		String validationQuery = databaseDriver.getValidationQuery();
//		try {
//			dataSource.setFilters("stat, wall");
//			if (validationQuery != null) {
//				dataSource.setTestOnBorrow(true);
//				dataSource.setValidationQuery(validationQuery);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return dataSource;
//	}
//
//	@Bean
//	public SqlSessionFactory sqlSessionFactory() throws Exception {
//		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//		bean.setDataSource(dataSource());
//		return bean.getObject();
//
//	}

}
