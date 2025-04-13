package com.github.shoe_shop.organization.organization;

import com.github.shoe_shop.exceptions.EntityAlreadyExistsException;
import com.github.shoe_shop.user.user.UserRole;
import com.github.shoe_shop.user.user_info.UserInfo;
import com.github.shoe_shop.user.user_info.UserInfoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrganizationService {
    private final OrganizationRepository organizationRepository;

    @Setter(onMethod_ = {@Autowired})
    private UserInfoService userInfoService;

    public Organization createOrganization(final Organization organization) {
        final boolean unpRegistered = organizationRepository.existsByUnp(organization.getUnp());
        if (unpRegistered) {
            throw new EntityAlreadyExistsException("Organization with given UNP already registered.");
        }
        final UserInfo head = userInfoService.getInfoById(organization.getOrganizationHead().getId());
        if (head == null) {
            throw new EntityNotFoundException("Head info not found. Maybe you're giving user id instead of user info id");
        }
        if (head.getUser().getRole()!= UserRole.ORGANIZATION_OWNER) {
            throw new RuntimeException("Given user cannot be owner of organization");
        }
        return organizationRepository.save(organization);
    }

    public Organization findByUnp(final String unp) {
        return organizationRepository.findByUnp(unp).orElseThrow(() -> new EntityNotFoundException("Organization with given UNP not found"));
    }
}
