package com.github.shoe_shop.security.login;

import com.github.shoe_shop.security.shared.BaseAuthenticationFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.io.IOException;

@Setter
public class LoginAuthenticationFilter extends BaseAuthenticationFilter {

    private RequestMatcher authenticationMatcher;

    private AuthenticationConverter authenticationConverter;

    private AuthenticationManager authenticationManager;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!authenticationMatcher.matches(request)) {
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

    @Override
    protected Authentication performeAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            final Authentication authentication = authenticationConverter.convert(request);
            if (authentication == null) {
                return null;
            }
            final Authentication currentAuth = authenticationManager.authenticate(authentication);
            return authentication;
        } catch (Exception e) {
            throw new BadCredentialsException("Bad credentials", e);
        }
    }

    @Override
    protected RequestMatcher getAuthenticationMatcher() {
        return authenticationMatcher;
    }
}
