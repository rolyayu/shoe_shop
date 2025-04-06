package com.github.shoe_shop.security.session;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "application.session")
public class SessionProperties {
    private long expirationMillis;
}
