package com.github.shoe_shop.organization;

import com.github.shoe_shop.exceptions.EntityAlreadyExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrganizationService {
    private final OrganizationRepository organizationRepository;

    public Organization createOrganization(final Organization organization) {
        final boolean unpRegistered = organizationRepository.existsByUnp(organization.getUnp());
        if (unpRegistered) {
            throw new EntityAlreadyExistsException("Organization with given UNP already registered.");
        }
        return organizationRepository.save(organization);
    }

    public Organization findByUnp(final String unp) {
        return organizationRepository.findByUnp(unp).orElseThrow(() -> new EntityNotFoundException("Organization with given UNP not found"));
    }

    public boolean existsByUnp(final String unp) {
        return organizationRepository.existsByUnp(unp);
    }
}
