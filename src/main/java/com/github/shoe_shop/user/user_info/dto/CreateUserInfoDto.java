package com.github.shoe_shop.user.user_info.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record CreateUserInfoDto(
        @NotBlank @Length(min = 5) @JsonProperty(value = "full_name", required = true) String fullName,
        @JsonProperty(value = "birth_date", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") @NotNull LocalDate birthDate,
        @JsonProperty(value = "gender", required = true) @NotBlank String gender
) {
}
