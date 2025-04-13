package com.github.shoe_shop.organization.workstations;

import com.github.shoe_shop.organization.branch.Branch;
import com.github.shoe_shop.organization.branch.BranchService;
import com.github.shoe_shop.organization.organization.Organization;
import com.github.shoe_shop.organization.organization.OrganizationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkstationService {
    private final BranchService branchService;

    private final WorkstationRepository workstationRepository;

    public Workstation create(final Workstation workstation, final String branchId) {
        final Branch foundedBranch = branchService.findById(branchId);
        workstation.setBranch(foundedBranch);
        return workstationRepository.save(workstation);
    }
}
