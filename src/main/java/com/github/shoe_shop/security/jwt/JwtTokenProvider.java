package com.github.shoe_shop.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtTokenProvider implements AuthenticationProvider {

    private final UserDetailsService detailsService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final UserDetails details = detailsService.loadUserByUsername(authentication.getPrincipal().toString());
        final TokenBody tokenBody = new TokenBody(details.getUsername(),details.getAuthorities());
        return new JwtTokenAuthentication(tokenBody);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(JwtTokenAuthentication.class);
    }
}
