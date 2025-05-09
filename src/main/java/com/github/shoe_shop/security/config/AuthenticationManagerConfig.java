package com.github.shoe_shop.security.config;

import com.github.shoe_shop.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

@Configuration
@RequiredArgsConstructor
public class AuthenticationManagerConfig {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    @Primary
    public AuthenticationManager myAuthBuilder(final ObjectPostProcessor<Object> objectObjectPostProcessor) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = new AuthenticationManagerBuilder(objectObjectPostProcessor);
        authenticationManagerBuilder.authenticationProvider(jwtTokenProvider);
        return  authenticationManagerBuilder.build();
    }
}
