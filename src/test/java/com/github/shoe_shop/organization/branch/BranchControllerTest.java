package com.github.shoe_shop.organization.branch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.shoe_shop.organization.branch.dto.CreateBranchDto;
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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import shared.TestContainerWithBranch;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BranchControllerTest extends TestContainerWithBranch {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

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
    @WithMockUser(authorities = "ADMINISTRATOR")
    public void createBranch_returnCreatedBranch_whenCurrentUserIsAdminAndInputCorrect() throws Exception {
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

        final CreateBranchDto createBranchDto = new CreateBranchDto("#1", branchHeadUserInfo.getId(), "Minsk", "SHOP");
        final String createDtoJson = MAPPER.writeValueAsString(createBranchDto);

        mockMvc.perform(post("/organizations/{unp}/branch", organization.getUnp())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createDtoJson))
                .andExpect(status().isCreated());
        assertEquals(1, branchRepository.count());
    }

    @Test
    @WithMockUser(authorities = {"ORGANIZATION_OWNER"}, username = "ShoeShop") //Username of TestContainerWithUser.user
    public void createBranch_returnCreatedBranch_whenCurrentUserIsThisOrgOwnerAndInputCorrect() throws Exception {
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

        final CreateBranchDto createBranchDto = new CreateBranchDto("#1", branchHeadUserInfo.getId(), "Minsk", "SHOP");
        final String createDtoJson = MAPPER.writeValueAsString(createBranchDto);

        mockMvc.perform(post("/organizations/{unp}/branch", organization.getUnp())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createDtoJson))
                .andExpect(status().isCreated());
        assertEquals(1, branchRepository.count());
    }

    @Test
    @WithMockUser(authorities = {"ORGANIZATION_OWNER"}, username = "newHeadUSER")
    public void createBranch_returnForbidden_whenCurrentUserIsNotThisOrgOwner() throws Exception {
        final User savedUser = userRepository.save(user);
        userInfo.setUser(savedUser);
        final UserInfo savedUserInfo = userInfoRepository.save(userInfo);
        organization.setOrganizationHead(savedUserInfo);
        organizationRepository.save(organization);

        final User headUser = userRepository.save(branchHeadUser);
        branchHeadInfo.setUser(headUser);
        final UserInfo branchHeadUserInfo = userInfoRepository.save(branchHeadInfo);

        final CreateBranchDto createBranchDto = new CreateBranchDto("#1", branchHeadUserInfo.getId(), "Minsk", "SHOP");
        final String createDtoJson = MAPPER.writeValueAsString(createBranchDto);

        final User newHeadUser = new User();
        newHeadUser.setRole(UserRole.ORGANIZATION_OWNER);
        newHeadUser.setUsername("newHeadUSER");
        newHeadUser.setEncodedPassword("newHeadUSER");
        final User savedNewHeadUser = userRepository.save(newHeadUser);

        final UserInfo newHeadInfo = new UserInfo();
        newHeadInfo.setUser(savedNewHeadUser);
        newHeadInfo.setGender(Gender.MALE);
        newHeadInfo.setBirthDate(LocalDate.of(1999, 2, 3));
        newHeadInfo.setFullName("NEWHEADINFO");
        userInfoRepository.save(newHeadInfo);

        mockMvc.perform(post("/organizations/{unp}/branch", organization.getUnp())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createDtoJson))
                .andExpect(status().isForbidden());
        assertEquals(0, branchRepository.count());
    }

    @Test
    @WithMockUser(authorities = {"ADMINISTRATOR"})
    public void createBranch_returnNotFound_whenUNPIsNotFound() throws Exception {
        final User savedUser = userRepository.save(user);
        userInfo.setUser(savedUser);
        final UserInfo savedUserInfo = userInfoRepository.save(userInfo);
        organization.setOrganizationHead(savedUserInfo);
        organizationRepository.save(organization);

        final User headUser = userRepository.save(branchHeadUser);
        branchHeadInfo.setUser(headUser);
        final UserInfo branchHeadUserInfo = userInfoRepository.save(branchHeadInfo);

        final CreateBranchDto createBranchDto = new CreateBranchDto("#1", branchHeadUserInfo.getId(), "Minsk", "SHOP");
        final String createDtoJson = MAPPER.writeValueAsString(createBranchDto);

        mockMvc.perform(post("/organizations/{unp}/branch", "00000000000000")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createDtoJson))
                .andExpect(status().isNotFound());
        assertEquals(0, branchRepository.count());
    }
}