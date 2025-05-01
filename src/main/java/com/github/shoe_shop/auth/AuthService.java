package com.github.shoe_shop.auth;

import com.github.shoe_shop.auth.dto.AuthDto;
import com.github.shoe_shop.exceptions.EntityAlreadyExistsException;
import com.github.shoe_shop.exceptions.IncorrectCredentialsException;
import com.github.shoe_shop.security.SecurityUser;
import com.github.shoe_shop.security.jwt.JwtService;
import com.github.shoe_shop.user.user.User;
import com.github.shoe_shop.user.user.UserRole;
import com.github.shoe_shop.user.user.UserService;
import lombok.RequiredArgsConstructor;
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
        try {
            return userService.createUser(user);
        } catch (EntityAlreadyExistsException e) {
            throw new IncorrectCredentialsException("Given username is already taken", e);
        }
    }

    public String signIn(final AuthDto authDto) {
        final User user = userService.findByUsername(authDto.username());
        return jwtService.generateToken(new SecurityUser(user));
    }
}
