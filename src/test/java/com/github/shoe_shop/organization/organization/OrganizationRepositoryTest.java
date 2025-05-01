package com.github.shoe_shop.organization.organization;

import com.github.shoe_shop.user.user.User;
import com.github.shoe_shop.user.user.UserRepository;
import com.github.shoe_shop.user.user_info.UserInfo;
import com.github.shoe_shop.user.user_info.UserInfoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shared.TestContainerOrganizationAndOwner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class OrganizationRepositoryTest extends TestContainerOrganizationAndOwner {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @BeforeEach
    public void resetDb() {
        organizationRepository.deleteAll();
        userInfoRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void findByUnp_returnNonEmptyOptional_whenOrganizationExists() {
        final User savedUser = userRepository.save(user);
        userInfo.setUser(savedUser);
        final UserInfo savedUserInfo = userInfoRepository.save(userInfo);
        organization.setOrganizationHead(savedUserInfo);
        final Organization savedOrganization = organizationRepository.save(organization);

        final Optional<Organization> foundedOrg = organizationRepository.findByUnp(organization.getUnp());

        assertTrue(foundedOrg.isPresent());
        assertEquals(savedOrganization, foundedOrg.get());
        assertNotNull(foundedOrg.get().getOrganizationHead());
    }

    @Test
    public void findByUnp_returnEmptyOptional_whenOrganizationNotExists() {
        final Optional<Organization> foundedOrg = organizationRepository.findByUnp(organization.getUnp());
        assertTrue(foundedOrg.isEmpty());
    }

    @Test
    public void existsByUnp_returnTrue_whenOrganizationExists() {
        final User savedUser = userRepository.save(user);
        userInfo.setUser(savedUser);
        final UserInfo savedUserInfo = userInfoRepository.save(userInfo);
        organization.setOrganizationHead(savedUserInfo);
        organizationRepository.save(organization);

        final boolean existsByUnp = organizationRepository.existsByUnp(organization.getUnp());

        assertTrue(existsByUnp);
    }

    @Test
    public void existsByUnp_returnFalse_whenOrganizationNotExists() {

        final boolean existsByUnp = organizationRepository.existsByUnp(organization.getUnp());

        assertFalse(existsByUnp);
    }
}