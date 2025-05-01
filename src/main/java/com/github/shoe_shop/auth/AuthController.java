package com.github.shoe_shop.auth;

import com.github.shoe_shop.auth.dto.AuthDto;
import com.github.shoe_shop.user.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService service;

    @PostMapping
    @RequestMapping("/sign-up")
    public User signUp(@RequestBody @Valid final AuthDto authDto) {
        return service.signUp(authDto);
    }

    @PostMapping
    @RequestMapping("/sign-in")
    public String signIn(@RequestBody @Valid final AuthDto authDto) {
        return service.signIn(authDto);
    }
}
