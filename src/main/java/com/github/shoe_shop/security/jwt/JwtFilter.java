package com.github.shoe_shop.security.jwt;

import com.github.shoe_shop.security.shared.BaseAuthenticationFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;

@Setter
public class JwtFilter extends BaseAuthenticationFilter {

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
    protected Authentication performeAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        final String token = extractToken(request);

        if (!StringUtils.hasLength(token)) {
            return null;
        }
        TokenBody tokenBody = null;
        try {
            tokenBody = jwtService.parseToken(token);
        } catch (Exception e) {
            throw new BadCredentialsException("Token is invalid");
        }
        final Authentication authToProcess = new JwtTokenAuthentication(tokenBody);

        return manager.authenticate(authToProcess);
    }

    @Override
    protected RequestMatcher getAuthenticationMatcher() {
        return skipMatcher;
    }
}
