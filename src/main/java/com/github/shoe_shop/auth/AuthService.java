package com.github.shoe_shop.auth;

import com.github.shoe_shop.auth.dto.AuthDto;
import com.github.shoe_shop.security.jwt.JwtService;
import com.github.shoe_shop.user.user.User;
import com.github.shoe_shop.user.user.UserRole;
import com.github.shoe_shop.user.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;

    private final PasswordEncoder encoder;

    private final JwtService jwtService;

    public User signUp(final AuthDto authDto) {
        final String encodedPassword = encoder.encode(authDto.rawPassword());
        final User user = User.builder()
                .username(authDto.username())
                .encodedPassword(encodedPassword)
                .role(UserRole.CASHIER)
                .build();
        return userService.createUser(user);
    }

    public String signIn(final Authentication authentication) {
        return jwtService.generateToken(authentication);
    }
}
