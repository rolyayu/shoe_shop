package com.github.shoe_shop.security.shared;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public abstract class BaseAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!isAuthenticationNeeded(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            final Authentication authentication = performeAuthentication(request, response);
            if (authentication == null) {
                filterChain.doFilter(request, response);
                return;
            }
            if (!authentication.isAuthenticated()) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unable to perform authentication for " + request.getRequestURI());
                return;
            }
            saveAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (AuthenticationException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
        }
    }

    protected abstract Authentication performeAuthentication(final HttpServletRequest request, final HttpServletResponse response) throws AuthenticationException;

    protected boolean isAuthenticationNeeded(final HttpServletRequest request) {
        return !authenticationAlreadyProvided() || getAuthenticationMatcher().matches(request);
    }

    protected abstract RequestMatcher getAuthenticationMatcher();

    protected boolean authenticationAlreadyProvided() {
        final SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) {
            return false;
        }
        final Authentication authentication = context.getAuthentication();
        if (authentication == null) {
            return false;
        }
        return authentication.isAuthenticated();
    }

    protected void saveAuthentication(final Authentication authentication) {
        final SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }
}