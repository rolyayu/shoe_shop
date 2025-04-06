package com.github.shoe_shop.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BaseSecurityBeansConfig {
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(4);
    }

}
