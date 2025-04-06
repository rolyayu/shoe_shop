package com.github.shoe_shop.security.login;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService detailsService;

    private final PasswordEncoder encoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final UserDetails details = detailsService.loadUserByUsername(authentication.getPrincipal().toString());

        if (!encoder.matches(authentication.getCredentials().toString(),details.getPassword())) {
            return null;
        }
        return new LoginAuthenticationToken(details);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(LoginAuthenticationToken.class);
    }
}
