package saas;

import jakarta.annotation.Resource;
import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.Servlet;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import saas.domain.Tenant;
import saas.domain.TenantRepository;
import saas.interceptor.RoutingDataSourceInterceptor;
import saas.interceptor.TenantResolveInterceptor;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Johnny
 * Date: 2017/8/17
 * Time: 0:04
 */
@EnableWebMvc
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Resource
    private DataSource dataSource;
    @Resource
    private DataSourceProperties properties;
    @Resource
    private TenantRepository tenantRepository;
    @Resource
    private MultipartConfigElement multipartConfig;

    @Bean(name = DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_BEAN_NAME)
    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServlet();
    }

    @Bean(name = DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_REGISTRATION_BEAN_NAME)
    public ServletRegistrationBean<Servlet> dispatcherServletRegistration() {
        Iterable<Tenant> tenants = this.tenantRepository.findAll();

        ServletRegistrationBean<Servlet> registration = new ServletRegistrationBean<>(dispatcherServlet(), getServletMappings(tenants));
        registration.setName(DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_BEAN_NAME);
        if (this.multipartConfig != null) {
            registration.setMultipartConfig(this.multipartConfig);
        }
        registerDataSource(tenants);
        return registration;
    }

    private void registerDataSource(Iterable<Tenant> tenants) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        for (Tenant tenant : tenants) {

            DataSourceBuilder<?> factory = DataSourceBuilder
                    .create(this.properties.getClassLoader())
                    .url(this.properties.getUrl())
                    .username(tenant.getDbu())
                    .password(tenant.getEdbpwd());
            targetDataSources.put(tenant.getBusinessName(), factory.build());
        }
        ((AbstractRoutingDataSource) this.dataSource).setTargetDataSources(targetDataSources);
        ((AbstractRoutingDataSource) this.dataSource).afterPropertiesSet();
    }

    private String[] getServletMappings(Iterable<Tenant> tenants) {
        List<String> mappings = new ArrayList<>();
        for (Tenant tenant : tenants) {
            mappings.add("/" + tenant.getBusinessName() + "/*");
        }
        mappings.add("/*");
        return mappings.toArray(new String[0]);
    }

    /**
     * Encrypt the business name to prevent from abusing accessing.
     * Need to resolve to the actual business name in {@link saas.interceptor.TenantResolveInterceptor#resolve(String)}
     */
    protected String encryptBusinessName(String businessName) {
        return businessName;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TenantResolveInterceptor());
        registry.addInterceptor(new RoutingDataSourceInterceptor());
    }
}
