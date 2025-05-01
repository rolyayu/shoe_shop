package com.github.shoe_shop.organization.workstations;

import com.github.shoe_shop.exceptions.EntityAlreadyExistsException;
import com.github.shoe_shop.exceptions.EntityNotFoundException;
import com.github.shoe_shop.organization.branch.Branch;
import com.github.shoe_shop.organization.branch.BranchRepository;
import com.github.shoe_shop.organization.organization.Organization;
import com.github.shoe_shop.organization.organization.OrganizationRepository;
import com.github.shoe_shop.user.user.User;
import com.github.shoe_shop.user.user.UserRepository;
import com.github.shoe_shop.user.user_info.UserInfo;
import com.github.shoe_shop.user.user_info.UserInfoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shared.TestContainerWithWorkstation;

import java.net.InetAddress;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WorkstationServiceTest extends TestContainerWithWorkstation {
    @Autowired
    private WorkstationRepository workstationRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private WorkstationService workstationService;

    @BeforeEach
    public void cleanUpDB() {
        workstationRepository.deleteAll();
        branchRepository.deleteAll();
        organizationRepository.deleteAll();
        userInfoRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void createWorkstation_returnCreatedWorkstation_whenOk() {
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

        final Branch createdBranch = branchRepository.save(branch);

        workstation.setBranch(createdBranch);
        workstation.setIp(InetAddress.getLoopbackAddress().getHostAddress());
        final Workstation createdWorkstation = workstationService.create(workstation, createdBranch.getBranchId());

        assertNotNull(createdWorkstation.getId());
        assertEquals(1, workstationRepository.count());
    }

    @Test
    public void createWorkstation_throwsEntityNotFound_whenBranchNotFound() {
        assertThrows(EntityNotFoundException.class, () -> workstationService.create(workstation, UUID.randomUUID().toString()));
    }

    @Test
    public void createWorkstation_throwsEntityAlreadyExists_whenIPIsUsed() {
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

        final Branch createdBranch = branchRepository.save(branch);

        workstation.setBranch(createdBranch);
        workstation.setIp(InetAddress.getLoopbackAddress().getHostAddress());
        workstationRepository.save(workstation);

        assertThrows(EntityAlreadyExistsException.class, () -> workstationService.create(workstation, UUID.randomUUID().toString()));
    }
}