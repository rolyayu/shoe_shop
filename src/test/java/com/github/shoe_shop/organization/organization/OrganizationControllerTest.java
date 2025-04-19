package com.github.shoe_shop.organization.organization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.shoe_shop.organization.organization.dto.CreateOrganizationDto;
import com.github.shoe_shop.user.user.User;
import com.github.shoe_shop.user.user.UserRepository;
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
import shared.TestContainerOrganizationAndOwner;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrganizationControllerTest extends TestContainerOrganizationAndOwner {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

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
    @WithMockUser(authorities = "ADMINISTRATOR")
    void createOrganization_returnCreatedAndOrganization_whenOrganizationSaved() throws Exception {
        final User savedUser = userRepository.save(user);
        userInfo.setUser(savedUser);
        final UserInfo savedUserInfo = userInfoRepository.save(userInfo);

        final CreateOrganizationDto createOrganizationDto = new CreateOrganizationDto(organization.getUnp()
                , organization.getOrganizationName()
                , organization.getAddress()
                , savedUserInfo.getId());
        final String createOrganizationJson = objectMapper.writeValueAsString(createOrganizationDto);

        mockMvc.perform(post("/organizations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createOrganizationJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.unp").value(organization.getUnp()));
    }

    @Test
    @WithMockUser(authorities = "ADMINISTRATOR")
    void findByUnp_returnOrganization_whenOrganizationExists() throws Exception {
        final User savedUser = userRepository.save(user);
        userInfo.setUser(savedUser);
        final UserInfo savedUserInfo = userInfoRepository.save(userInfo);
        organization.setOrganizationHead(savedUserInfo);
        organizationRepository.save(organization);

        mockMvc.perform(get("/organizations/{unp}", organization.getUnp()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.unp").value(organization.getUnp()));
    }

    @Test
    @WithMockUser(authorities = "ADMINISTRATOR")
    void findByUnp_returnNotFound_whenOrganizationNotExists() throws Exception {
        mockMvc.perform(get("/organizations/{unp}", organization.getUnp()))
                .andExpect(status().isNotFound());
    }
}