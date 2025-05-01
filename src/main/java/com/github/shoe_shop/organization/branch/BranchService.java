package com.github.shoe_shop.organization.branch;

import com.github.shoe_shop.exceptions.BadArgumentsException;
import com.github.shoe_shop.exceptions.EntityAlreadyExistsException;
import com.github.shoe_shop.exceptions.EntityNotFoundException;
import com.github.shoe_shop.organization.organization.Organization;
import com.github.shoe_shop.organization.organization.OrganizationService;
import com.github.shoe_shop.user.user.UserRole;
import com.github.shoe_shop.user.user_info.UserInfo;
import com.github.shoe_shop.user.user_info.UserInfoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BranchService {
    private final BranchRepository branchRepository;
    private final OrganizationService organizationService;
    private final UserInfoService userInfoService;

    @Transactional
    public Branch createBranch(final Branch branch, final String orgUnp) {
        final Organization organization = organizationService.findByUnp(orgUnp);

        final UserInfo headOfBranch = userInfoService.getInfoById(branch.getBranchHead().getId());
        if (headOfBranch.getUser().getRole() != UserRole.HEAD_OF_BRANCH) {
            throw new BadArgumentsException("Given user cannot be head of branch");
        }
        final boolean headAlreadyHasBranch = branchRepository.findByBranchHead(headOfBranch).isPresent();
        if (headAlreadyHasBranch) {
            throw new BadArgumentsException("Given head of branch already has it's own branch");
        }

        final boolean branchAlreadyExists = branchRepository.findByOrganizationUnpAndBranchNo(orgUnp, branch.getBranchNo()).isPresent();
        if (branchAlreadyExists) {
            throw new EntityAlreadyExistsException("Branch with given No already exists in this organization.");
        }

        branch.setOrganization(organization);
        branch.setBranchHead(headOfBranch);

        return branchRepository.save(branch);
    }

    public Branch findById(final String branchId) {
        return branchRepository.findByBranchId(branchId)
                .orElseThrow(() -> new EntityNotFoundException("Branch not found with id: " + branchId));
    }
}
