package com.github.shoe_shop.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.SignatureException;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;

@Service
public class JwtService {

    private final Key secret;

    @Value("${application.jwt.expiration-millis}")
    private long ttl;

    public JwtService(@Value("${application.jwt.secret}") final String secretValue) {
        this.secret = Keys.hmacShaKeyFor(secretValue.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(final Authentication authentication) {
        return Jwts.builder()
                .claim("username", authentication.getPrincipal())
                .claim("user_roles", authentication.getAuthorities())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusMillis(ttl)))
                .signWith(secret)
                .compact();
    }

    public String generateToken(final UserDetails details) {
        return Jwts.builder()
                .claim("username", details.getUsername())
                .claim("user_roles", details.getAuthorities())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusMillis(ttl)))
                .signWith(secret)
                .compact();
    }

    public Claims extractClaims(final String token) {
        Claims jws = Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return jws;
    }

    public TokenBody parseToken(final String token) {
        final Claims claimsJws = extractClaims(token);
        final String username = claimsJws.get("username").toString();
        final Collection<? extends GrantedAuthority> roles = (Collection<? extends GrantedAuthority>) claimsJws.get("user_roles");
        return new TokenBody(username, roles);
    }
}