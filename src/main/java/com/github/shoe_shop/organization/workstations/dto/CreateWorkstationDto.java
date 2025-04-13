package com.github.shoe_shop.organization.workstations.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.shoe_shop.base.constraints.IP;
import org.hibernate.validator.constraints.UUID;

public record CreateWorkstationDto(
        @JsonProperty(required = true) @IP String ip,
        @JsonProperty(required = true) @UUID String branchId
) {
}
