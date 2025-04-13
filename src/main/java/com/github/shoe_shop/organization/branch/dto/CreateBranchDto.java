package com.github.shoe_shop.organization.branch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CreateBranchDto(
        @JsonProperty(value = "branch_no", required = true) @NotBlank String branchNo,
        @JsonProperty(value = "branch_head_id", required = true) @Min(1) long branchHeadId,
        @JsonProperty(value = "branch_address", required = true) @NotBlank String branchAddress,
        @JsonProperty(value = "branch_type", required = true) @NotBlank String type
) {
}
