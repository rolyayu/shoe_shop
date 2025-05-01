package com.github.shoe_shop.organization.workstations;

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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import shared.TestContainerWithWorkstation;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class WorkstationControllerTest extends TestContainerWithWorkstation {

    @Autowired
    private MockMvc mockMvc;

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

    @BeforeEach
    public void cleanUpDB() {
        workstationRepository.deleteAll();
        branchRepository.deleteAll();
        organizationRepository.deleteAll();
        userInfoRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(authorities = "ADMINISTRATOR")
    public void createWorkstation_returnCreatedWorkstation_whenOK() throws Exception {
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

        final String IP = "127.0.0.1";

        mockMvc.perform(post("/workstations/current_ip/branch/{branch_id}", createdBranch.getBranchId())
                        .header("X-FORWARDED-FOR", IP))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.ip").value(IP));
    }

    @Test
    @WithMockUser(authorities = "ADMINISTRATOR")
    public void createWorkstation_returnBadRequest_whenIpIsAlreadyTaken() throws Exception {
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

        final String IP = "127.0.0.1";
        workstation.setBranch(createdBranch);
        workstation.setIp(IP);
        workstationRepository.save(workstation);

        mockMvc.perform(post("/workstations/current_ip/branch/{branch_id}", createdBranch.getBranchId())
                        .header("X-FORWARDED-FOR", IP))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(authorities = "ADMINISTRATOR")
    public void createWorkstation_returnNotFound_whenBranchNotExists() throws Exception {
        final String IP = "127.0.0.1";
        mockMvc.perform(post("/workstations/current_ip/branch/{branch_id}", UUID.randomUUID().toString())
                        .header("X-FORWARDED-FOR", IP))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = "ADMINISTRATOR")
    public void createWorkstationByIp_returnCreatedWorkstation_whenOK() throws Exception {
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

        final String IP = "127.0.0.1";

        mockMvc.perform(post("/workstations/ip/{ip}/branch/{branch_id}", IP, createdBranch.getBranchId()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.ip").value(IP));
    }

    @Test
    @WithMockUser(authorities = "ADMINISTRATOR")
    public void createWorkstationByIp_returnBadRequest_whenIpIsAlreadyTaken() throws Exception {
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

        final String IP = "127.0.0.1";
        workstation.setBranch(createdBranch);
        workstation.setIp(IP);
        workstationRepository.save(workstation);

        mockMvc.perform(post("/workstations/ip/{ip}/branch/{branch_id}", IP, createdBranch.getBranchId()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(authorities = "ADMINISTRATOR")
    public void createWorkstationByIp_returnNotFound_whenBranchNotExists() throws Exception {
        final String IP = "127.0.0.1";
        mockMvc.perform(post("/workstations/ip/{ip}/branch/{branch_id}", IP, UUID.randomUUID().toString()))
                .andExpect(status().isNotFound());
    }
}