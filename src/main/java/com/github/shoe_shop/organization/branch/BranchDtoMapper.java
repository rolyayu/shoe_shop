package com.github.shoe_shop.organization.branch;

import com.github.shoe_shop.organization.branch.dto.CreateBranchDto;
import com.github.shoe_shop.user.user_info.UserInfo;
import org.springframework.stereotype.Service;

@Service
public class BranchDtoMapper {
    public Branch mapCreateDtoToEntity(final CreateBranchDto createBranchDto) {
        final Branch branch = new Branch();
        branch.setBranchAddress(createBranchDto.branchAddress());
        branch.setBranchNo(createBranchDto.branchNo());
        branch.setType(BranchType.valueOf(createBranchDto.type()));

        final UserInfo branchHead = new UserInfo();
        branchHead.setId(createBranchDto.branchHeadId());
        branch.setBranchHead(branchHead);

        return branch;
    }
}
