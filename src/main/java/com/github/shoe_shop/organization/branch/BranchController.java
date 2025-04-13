package com.github.shoe_shop.organization.branch;

import com.github.shoe_shop.base.constraints.UNP;
import com.github.shoe_shop.organization.branch.dto.CreateBranchDto;
import com.github.shoe_shop.web.annotations.OrganizationOwnerValidUNP;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/organizations/{unp}/branch")
@RequiredArgsConstructor
public class BranchController {
    private final BranchService branchService;
    private final BranchDtoMapper branchDtoMapper;

    @PostMapping
    @Secured({"ORGANIZATION_OWNER", "ADMINISTRATOR"})
    @OrganizationOwnerValidUNP
    public Branch createBranch(@Valid @UNP @PathVariable final String unp, @Valid @RequestBody final CreateBranchDto createBranchDto) {
        final Branch branchToCreate = branchDtoMapper.mapCreateDtoToEntity(createBranchDto);
        return branchService.createBranch(branchToCreate, unp);
    }
}
