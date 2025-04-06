package com.github.shoe_shop.user.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserDto(
        @Size(min = 8, max = 40,message = "Username length should be within 8 and 40") String username,
        @Size(min = 8, max = 40, message = "Password length should be within 8 and 40") String password,
        @NotBlank String role
) {
}
