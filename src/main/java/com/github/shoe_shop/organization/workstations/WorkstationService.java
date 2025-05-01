package com.github.shoe_shop.organization.workstations;

import com.github.shoe_shop.exceptions.EntityAlreadyExistsException;
import com.github.shoe_shop.organization.branch.Branch;
import com.github.shoe_shop.organization.branch.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkstationService {
    private final BranchService branchService;

    private final WorkstationRepository workstationRepository;

    public Workstation create(final Workstation workstation, final String branchId) {
        if (workstationRepository.existsByIp(workstation.getIp())) {
            throw new EntityAlreadyExistsException("Workstation " + workstation.getIp() + " already exists");
        }
        final Branch foundedBranch = branchService.findById(branchId);
        workstation.setBranch(foundedBranch);
        return workstationRepository.save(workstation);
    }
}
