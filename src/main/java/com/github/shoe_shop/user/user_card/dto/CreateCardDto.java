package com.github.shoe_shop.user.user_card.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.shoe_shop.base.constraints.CardNo;
import com.github.shoe_shop.base.constraints.IP;
import com.github.shoe_shop.base.constraints.UNP;

public record CreateCardDto(
        @JsonProperty("card_no") @CardNo String cardNo,
        @JsonProperty(value = "allowed_workstation_ip", required = false) @IP String workstationIP,
        @JsonProperty(value = "worker_id", required = false) Long workerId,
        @JsonProperty("supposed_organization_unp") @UNP String unp
) {
}
