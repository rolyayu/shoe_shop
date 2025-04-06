package com.github.shoe_shop.user.user_info.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.shoe_shop.base.constraints.UNP;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record CreateUserInfoDto(
        @NotBlank @Length(min = 5) @JsonProperty("full_name") String fullName,
        @UNP @JsonProperty("organization_unp") String organizationUnp,
        @NotBlank @Length(min = 3) @JsonProperty("job_position") String jopPos

) {
}
