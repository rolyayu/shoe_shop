package com.github.shoe_shop.organization.workstations;

import com.github.shoe_shop.organization.Organization;
import com.github.shoe_shop.organization.OrganizationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkstationService {
    private final OrganizationService organizationService;

    private final WorkstationRepository workstationRepository;

    public Workstation create(final Workstation workstation, final String organizationUnp) {
        Organization foundedOrganization = organizationService.findByUnp(organizationUnp);
        workstation.setOrganization(foundedOrganization);
        return workstationRepository.save(workstation);
    }

    public Workstation findByIP(final String ip) {
        return workstationRepository.findById(ip)
                .orElseThrow(() -> new EntityNotFoundException("No workstation attached to IP " + ip));
    }
}
