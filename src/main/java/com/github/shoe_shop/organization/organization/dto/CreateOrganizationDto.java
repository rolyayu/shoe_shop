package com.github.shoe_shop.organization.organization.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.shoe_shop.shared.constraints.UNP;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CreateOrganizationDto(
        @NotBlank @UNP String unp,
        @NotBlank String name,
        @NotBlank String address,
        @JsonProperty("head_info_id") @Min(1) long headInfoId
) {
}
