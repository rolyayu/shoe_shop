package com.github.shoe_shop.organization.branch;

import com.github.shoe_shop.exceptions.BadArgumentsException;
import com.github.shoe_shop.exceptions.EntityAlreadyExistsException;
import com.github.shoe_shop.organization.organization.Organization;
import com.github.shoe_shop.organization.organization.OrganizationRepository;
import com.github.shoe_shop.user.user.User;
import com.github.shoe_shop.user.user.UserRepository;
import com.github.shoe_shop.user.user.UserRole;
import com.github.shoe_shop.user.user_info.Gender;
import com.github.shoe_shop.user.user_info.UserInfo;
import com.github.shoe_shop.user.user_info.UserInfoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shared.TestContainerWithBranch;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BranchServiceTest extends TestContainerWithBranch {
    @Autowired
    private BranchService branchService;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @BeforeEach
    public void cleanUpDB() {
        branchRepository.deleteAll();
        organizationRepository.deleteAll();
        userInfoRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void createBranch_returnCreatedBranch_whenAllConditionsFulfilled() {
        final User savedUser = userRepository.save(user);
        userInfo.setUser(savedUser);
        final UserInfo savedUserInfo = userInfoRepository.save(userInfo);
        organization.setOrganizationHead(savedUserInfo);
        final Organization savedOrganization = organizationRepository.save(organization);

        final User headUser = userRepository.save(branchHeadUser);
        branchHeadInfo.setUser(headUser);
        final UserInfo branchHeadUserInfo = userInfoRepository.save(branchHeadInfo);

        branch.setOrganization(savedOrganization);
        branch.setBranchHead(branchHeadUserInfo);

        final Branch createdBranch = branchService.createBranch(branch, organization.getUnp());
        assertNotNull(createdBranch);
    }

    @Test
    public void createBranch_throwsBadArgument_whenHeadHasWrongRole() {
        final User savedUser = userRepository.save(user);
        userInfo.setUser(savedUser);
        final UserInfo savedUserInfo = userInfoRepository.save(userInfo);
        organization.setOrganizationHead(savedUserInfo);
        final Organization savedOrganization = organizationRepository.save(organization);

        branchHeadUser.setRole(UserRole.WAREHOUSEMAN);
        final User headUser = userRepository.save(branchHeadUser);
        branchHeadInfo.setUser(headUser);
        final UserInfo branchHeadUserInfo = userInfoRepository.save(branchHeadInfo);

        branch.setOrganization(savedOrganization);
        branch.setBranchHead(branchHeadUserInfo);

        assertThrows(BadArgumentsException.class, () -> branchService.createBranch(branch,organization.getUnp()));
        branchHeadUser.setRole(UserRole.HEAD_OF_BRANCH);

    }

    @Test
    public void createBranch_throwsBadArgument_whenHeadAlreadyHasBranch() {
        final User savedUser = userRepository.save(user);
        userInfo.setUser(savedUser);
        final UserInfo savedUserInfo = userInfoRepository.save(userInfo);
        organization.setOrganizationHead(savedUserInfo);
        final Organization savedOrganization = organizationRepository.save(organization);

        final User headUser = userRepository.save(branchHeadUser);
        branchHeadInfo.setUser(headUser);
        final UserInfo branchHeadUserInfo = userInfoRepository.save(branchHeadInfo);

        branch.setOrganization(savedOrganization);
        branch.setBranchHead(branchHeadUserInfo);
        branchRepository.save(branch);
        assertThrows(BadArgumentsException.class, () -> branchService.createBranch(branch,organization.getUnp()));
    }

    @Test
    public void createBranch_throwsEntityAlreadyExists_whenBranchAlreadyExists() {
        final User savedUser = userRepository.save(user);
        userInfo.setUser(savedUser);
        final UserInfo savedUserInfo = userInfoRepository.save(userInfo);
        organization.setOrganizationHead(savedUserInfo);
        final Organization savedOrganization = organizationRepository.save(organization);

        final User headUser = userRepository.save(branchHeadUser);
        branchHeadInfo.setUser(headUser);
        final UserInfo branchHeadUserInfo = userInfoRepository.save(branchHeadInfo);

        branch.setOrganization(savedOrganization);
        branch.setBranchHead(branchHeadUserInfo);
        branchRepository.save(branch);

        final User newHeadUser = new User();
        newHeadUser.setRole(UserRole.HEAD_OF_BRANCH);
        newHeadUser.setUsername("newHeadUSER");
        newHeadUser.setEncodedPassword("newHeadUSER");
        final User savedNewHeadUser = userRepository.save(newHeadUser);

        final UserInfo newHeadInfo = new UserInfo();
        newHeadInfo.setUser(savedNewHeadUser);
        newHeadInfo.setGender(Gender.MALE);
        newHeadInfo.setBirthDate(LocalDate.of(1999,2,3));
        newHeadInfo.setFullName("NEWHEADINFO");
        final UserInfo savedNewHeadInfo = userInfoRepository.save(newHeadInfo);

        final Branch newBranch = new Branch();
        newBranch.setOrganization(savedOrganization);
        newBranch.setBranchHead(savedNewHeadInfo);
        newBranch.setType(BranchType.SHOP);
        newBranch.setBranchNo(branch.getBranchNo());
        newBranch.setBranchAddress("Address");

        assertThrows(EntityAlreadyExistsException.class, () -> branchService.createBranch(newBranch,organization.getUnp()));
    }
}