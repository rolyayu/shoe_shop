package com.github.shoe_shop.organization.organization;

import com.github.shoe_shop.exceptions.BadArgumentsException;
import com.github.shoe_shop.exceptions.EntityAlreadyExistsException;
import com.github.shoe_shop.user.user.User;
import com.github.shoe_shop.user.user.UserRepository;
import com.github.shoe_shop.user.user.UserRole;
import com.github.shoe_shop.user.user_info.UserInfo;
import com.github.shoe_shop.user.user_info.UserInfoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shared.TestContainerOrganizationAndOwner;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class OrganizationServiceTest extends TestContainerOrganizationAndOwner {
    @Autowired
    private OrganizationService organizationService;

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
    public void createOrganization_returnSavedOrganization_whenAllConditionsFulfilled() {
        setUserRole(UserRole.ORGANIZATION_OWNER);
        final User savedUser = userRepository.save(user);
        userInfo.setUser(savedUser);
        final UserInfo savedUserInfo = userInfoRepository.save(userInfo);
        organization.setOrganizationHead(savedUserInfo);

        final Organization savedOrganization = organizationService.createOrganization(organization);

        assertNotNull(savedOrganization);
    }

    @Test
    public void createOrganization_throwsEntityAlreadyExists_whenUNPIsTaken() {
        final User savedUser = userRepository.save(user);
        userInfo.setUser(savedUser);
        final UserInfo savedUserInfo = userInfoRepository.save(userInfo);
        organization.setOrganizationHead(savedUserInfo);
        organizationRepository.save(organization);

        assertThrows(EntityAlreadyExistsException.class, () -> organizationService.createOrganization(organization));
    }

    @Test
    public void createOrganization_throwsBadArguments_whenUserHasWrongRole() {
        setUserRole(UserRole.CASHIER);
        final User savedUser = userRepository.save(user);
        userInfo.setUser(savedUser);
        final UserInfo savedUserInfo = userInfoRepository.save(userInfo);
        organization.setOrganizationHead(savedUserInfo);

        assertThrows(BadArgumentsException.class, () -> organizationService.createOrganization(organization));
    }
}