package com.github.shoe_shop.security.jwt;

import com.github.shoe_shop.security.jwt.TokenBody;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@AllArgsConstructor
public class JwtTokenAuthentication implements Authentication {

    private TokenBody tokenBody;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return tokenBody.roles();
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return tokenBody.username();
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return tokenBody.username();
    }
}
