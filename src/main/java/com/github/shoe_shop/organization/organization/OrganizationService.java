package com.github.shoe_shop.organization.organization;

import com.github.shoe_shop.exceptions.BadArgumentsException;
import com.github.shoe_shop.exceptions.EntityAlreadyExistsException;
import com.github.shoe_shop.exceptions.EntityNotFoundException;
import com.github.shoe_shop.user.user.UserRole;
import com.github.shoe_shop.user.user_info.UserInfo;
import com.github.shoe_shop.user.user_info.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrganizationService {
    private final OrganizationRepository organizationRepository;

    private final UserInfoService userInfoService;

    public Organization createOrganization(final Organization organization) {
        final boolean unpRegistered = organizationRepository.existsByUnp(organization.getUnp());
        if (unpRegistered) {
            throw new EntityAlreadyExistsException("Organization with given UNP already registered.");
        }
        final UserInfo head = userInfoService.getInfoById(organization.getOrganizationHead().getId());
        if (head.getUser().getRole() != UserRole.ORGANIZATION_OWNER) {
            throw new BadArgumentsException("Given user cannot be owner of organization");
        }
        return organizationRepository.save(organization);
    }

    public Organization findByUnp(final String unp) {
        return organizationRepository.findByUnp(unp).orElseThrow(() -> new EntityNotFoundException("Organization with given UNP not found"));
    }
}
