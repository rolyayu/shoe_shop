package com.github.shoe_shop.user.user_info;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.shoe_shop.user.user.User;
import com.github.shoe_shop.user.user.UserRepository;
import com.github.shoe_shop.user.user_info.dto.CreateUserInfoDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import shared.TestContainerWithUserAndInfo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserInfoControllerTest extends TestContainerWithUserAndInfo {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private UserRepository userRepository;


    @BeforeEach
    @Transactional
    void setUpDB() {
        userInfoRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(authorities = "ADMINISTRATOR")
    void createInfo_returnCreatedInfo_ifNotExistsForUser() throws Exception {
        final User createdUser = userRepository.save(user);
        final CreateUserInfoDto createUserInfoDto = new CreateUserInfoDto(userInfo.getFullName(),
                userInfo.getBirthDate(), userInfo.getGender().name());
        mapper.registerModule(new JavaTimeModule());
        final String jsonCreateDto = mapper.writeValueAsString(createUserInfoDto);

        mockMvc.perform(post("/users/{userId}/info", createdUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCreateDto))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty());
        assertEquals(1, userInfoRepository.count());
    }
}