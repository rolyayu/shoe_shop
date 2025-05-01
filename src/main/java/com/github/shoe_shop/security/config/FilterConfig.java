package com.github.shoe_shop.security.config;

import com.github.shoe_shop.base.ApiEndpoints;
import com.github.shoe_shop.security.jwt.JwtFilter;
import com.github.shoe_shop.security.jwt.JwtService;
import com.github.shoe_shop.security.login.LoginAuthenticationFilter;
import com.github.shoe_shop.security.shared.SkipRequestMatcher;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.AuthenticationConverter;
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

    @Setter(onMethod_ = {@Qualifier("loginAuthenticationConverter"), @Autowired})
    private AuthenticationConverter loginAuthenticationConverter;

    @Bean
    public JwtFilter jwtFilter()  {
        final JwtFilter filter = new JwtFilter();
        filter.setJwtService(jwtService);
        filter.setManager(manager);
        final RequestMatcher signInMatcher = new AntPathRequestMatcher(apiEndpoints.getAuthSignIn());
        final RequestMatcher signUpMatcher = new AntPathRequestMatcher(apiEndpoints.getAuthSignUp());
        final RequestMatcher skipMatcher = RequestMatchers.anyOf(signInMatcher,signUpMatcher);
        filter.setSkipMatcher(new SkipRequestMatcher(skipMatcher));
        return filter;
    }

    @Bean
    public LoginAuthenticationFilter loginAuthenticationFilter()  {
        final LoginAuthenticationFilter filter = new LoginAuthenticationFilter();
        final RequestMatcher matcher = new AntPathRequestMatcher(apiEndpoints.getAuthSignIn());
        filter.setAuthenticationConverter(loginAuthenticationConverter);
        filter.setAuthenticationManager(manager);
        filter.setAuthenticationMatcher(matcher);
        return filter;
    }


}
