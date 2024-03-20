package com.johnny.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "security.config")
@Data
public class SecuritySettings {
    private String logoutSuccessUrl = "/logout";
    private String permitAll = "/api";
    private String deniedPage = "/deny";
    private String urlRoles;
}
