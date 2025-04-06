package com.github.shoe_shop.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Setter
public class JwtFilter extends OncePerRequestFilter {

    private static final String BEARER = "Bearer ";
    private static final int TOKEN_OFFSET = BEARER.length();

    private JwtService jwtService;

    private RequestMatcher skipMatcher;

    private AuthenticationManager manager;

    public String extractToken(final HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.hasLength(authHeader) || authHeader.equals(BEARER) || !authHeader.startsWith(BEARER)) {
            return "";
        }
        return authHeader.substring(TOKEN_OFFSET);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (skipMatcher.matches(request)) {
            filterChain.doFilter(request,response);
            return;
        }
        final  String token = extractToken(request);

        //todo добавить кэш

        if (!StringUtils.hasLength(token)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Token is not provided");
            return;
        }
        TokenBody tokenBody = null;
        try {
            tokenBody = jwtService.parseToken(token);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Token is not provided");
            return;
        }
        final Authentication authToProcess = new JwtTokenAuthentication(tokenBody);
        final Authentication currentAuth = manager.authenticate(authToProcess);

        if (currentAuth==null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Invalid token");
            return;
        }

        final SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(currentAuth);
        SecurityContextHolder.setContext(context);

        filterChain.doFilter(request,response);
    }
}
