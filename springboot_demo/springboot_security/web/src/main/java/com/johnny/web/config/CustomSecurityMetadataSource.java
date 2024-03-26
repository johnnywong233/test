package com.johnny.web.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class CustomSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    private Map<String, Collection<ConfigAttribute>> resourceMap;
    private final PathMatcher pathMatcher = new AntPathMatcher();

    private final String urlRoles;

    public CustomSecurityMetadataSource(String urlRoles) {
        super();
        this.urlRoles = urlRoles;
        resourceMap = loadResourceMatchAuthority();
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    private Map<String, Collection<ConfigAttribute>> loadResourceMatchAuthority() {

        Map<String, Collection<ConfigAttribute>> map = new HashMap<>();

        if (urlRoles != null && !urlRoles.isEmpty()) {
            String[] resouces = urlRoles.split(";");
            for (String resource : resouces) {
                String[] urls = resource.split("=");
                String[] roles = urls[1].split(",");
                Collection<ConfigAttribute> list = new ArrayList<>();
                for (String role : roles) {
                    ConfigAttribute config = new SecurityConfig(role.trim());
                    list.add(config);
                }
                //key：url, value：roles
                map.put(urls[0].trim(), list);
            }
        } else {
            log.error("'securityconfig.urlRoles' must be set");
        }

        log.info("Loaded UrlRoles Resources.");
        return map;

    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object)
            throws IllegalArgumentException {
        String url = ((FilterInvocation) object).getRequestUrl();

        log.debug("request url is  " + url);

        if (resourceMap == null) {
            resourceMap = loadResourceMatchAuthority();
        }

        for (String resURL : resourceMap.keySet()) {
            if (pathMatcher.match(resURL, url)) {
                return resourceMap.get(resURL);
            }
        }
        return resourceMap.get(url);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
