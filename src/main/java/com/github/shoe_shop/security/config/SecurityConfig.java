package com.github.shoe_shop.security.config;

import com.github.shoe_shop.base.ApiEndpoints;
import com.github.shoe_shop.security.jwt.JwtFilter;
import com.github.shoe_shop.security.login.LoginAuthenticationFilter;
import com.github.shoe_shop.user.user.User;
import com.github.shoe_shop.user.user.UserRole;
import com.github.shoe_shop.user.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@RequiredArgsConstructor
@DependsOn({"filterConfig"})
@EnableMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true)
public class SecurityConfig implements CommandLineRunner {

    private final UserService userService;

    private final JwtFilter jwtFilter;

    private final LoginAuthenticationFilter loginAuthenticationFilter;

    private final PasswordEncoder encoder;

    private final ApiEndpoints apiEndpoints;

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(req -> req
                        .requestMatchers(apiEndpoints.getAuthSignUp()).permitAll()
                        .anyRequest().authenticated())
//                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(loginAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    @Profile("test")
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(new AntPathRequestMatcher("/h2-console/**"));
    }

    @Override
    public void run(String... args) throws Exception {
        userService.getUserRepository().deleteAll();
        final User userToCreate = User.builder()
                .username("rolyayu12345")
                .encodedPassword(encoder.encode("password"))
                .role(UserRole.ADMINISTRATOR)
                .build();
        userService.createUser(userToCreate);
    }
}
