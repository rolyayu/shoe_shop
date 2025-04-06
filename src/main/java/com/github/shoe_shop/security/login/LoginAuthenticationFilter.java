package com.github.shoe_shop.security.login;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Setter
public class LoginAuthenticationFilter extends OncePerRequestFilter {

    private RequestMatcher matcher;

    private AuthenticationConverter authenticationConverter;

    private AuthenticationManager authenticationManager;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!matcher.matches(request)) {
            filterChain.doFilter(request,response);
            return;
        }

        final Authentication authentication = authenticationConverter.convert(request);
        if (authentication==null) {
            filterChain.doFilter(request,response);
            return;
        }

        final Authentication currentAuth = authenticationManager.authenticate(authentication);
        if (currentAuth==null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Incorrect credentials");
            return;
        }
        final SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(currentAuth);
        SecurityContextHolder.setContext(context);
        filterChain.doFilter(request,response);
    }
}
