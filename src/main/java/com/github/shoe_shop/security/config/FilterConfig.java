package com.github.shoe_shop.security.config;

import com.github.shoe_shop.shared.ApiEndpoints;
import com.github.shoe_shop.security.jwt.JwtFilter;
import com.github.shoe_shop.security.jwt.JwtService;
import com.github.shoe_shop.security.shared.SkipRequestMatcher;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatchers;

@Configuration
@RequiredArgsConstructor
@DependsOn({"apiEndpoints"})
public class FilterConfig {

    private final JwtService jwtService;

    private final ApiEndpoints apiEndpoints;

    private final AuthenticationManager manager;


    @Bean
    public JwtFilter jwtFilter() {
        final JwtFilter filter = new JwtFilter();
        filter.setJwtService(jwtService);
        filter.setManager(manager);
        final RequestMatcher signInMatcher = new AntPathRequestMatcher(apiEndpoints.getAuthSignIn());
        final RequestMatcher signUpMatcher = new AntPathRequestMatcher(apiEndpoints.getAuthSignUp());
        final RequestMatcher skipMatcher = RequestMatchers.anyOf(signInMatcher, signUpMatcher);
        filter.setSkipMatcher(new SkipRequestMatcher(skipMatcher));
        return filter;
    }
}
