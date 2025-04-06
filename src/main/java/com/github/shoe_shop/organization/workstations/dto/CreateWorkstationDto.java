package com.github.shoe_shop.organization.workstations.dto;

import com.github.shoe_shop.base.constraints.IP;
import com.github.shoe_shop.base.constraints.UNP;

public record CreateWorkstationDto(
        @IP String ip,
        @UNP String organizationUnp
) {
}
