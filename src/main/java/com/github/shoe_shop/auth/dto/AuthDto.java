package com.github.shoe_shop.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AuthDto(
        @NotNull @Size(min = 8, max = 40, message = "Password length should be within 8 and 40 symbols")
        String username,
        @JsonProperty("password") @NotNull @Size(min = 8, max = 40, message = "Password length should be within 8 and 40 symbols")
        String rawPassword
) {
}
