package com.github.shoe_shop.shared;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "api")
@PropertySource(value = "classpath:/endpoints.properties")
public class ApiEndpoints {
    private String authSignIn;
    private String authSignUp;
}
