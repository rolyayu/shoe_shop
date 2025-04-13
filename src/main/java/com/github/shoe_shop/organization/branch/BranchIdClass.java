package com.github.shoe_shop.organization.branch;

import com.github.shoe_shop.organization.organization.Organization;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class BranchIdClass {
    private Organization organization;

    private String branchNo;
}
