package com.github.shoe_shop.security.jwt;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public record TokenBody(
        String username,
        Collection<? extends GrantedAuthority> roles
) {
}
