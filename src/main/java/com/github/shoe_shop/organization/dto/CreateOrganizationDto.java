package com.github.shoe_shop.organization.dto;

import com.github.shoe_shop.base.constraints.UNP;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateOrganizationDto(
        @NotBlank @UNP String unp,
        @NotBlank String name,
        @NotBlank String address
) {
}
