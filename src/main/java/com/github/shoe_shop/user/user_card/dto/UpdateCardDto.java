package com.github.shoe_shop.user.user_card.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.shoe_shop.base.constraints.IP;

public record UpdateCardDto(
        @JsonProperty(value = "allowed_workstation_ip", required = false) @IP String workstationIP,
        @JsonProperty(value = "worker_id", required = false) Long workerId
) {
}