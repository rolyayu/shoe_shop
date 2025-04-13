package com.github.shoe_shop.user.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.github.shoe_shop.user.user.dto.CreateUserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import shared.BaseContainerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest extends BaseContainerTest {

    @Autowired
    private MockMvc mvc;

    private User user;

    @BeforeEach
    void setUpUser() {
        user = new User();
        user.setUsername("administrator");
        user.setEncodedPassword("password");
        user.setRole(UserRole.ADMINISTRATOR);
    }

    @Test
    @WithMockUser(authorities = {"ADMINISTRATOR"})
    void createUser_returnOkAndCreatedUser() throws Exception {
        final CreateUserDto createUserDto = new CreateUserDto(
                user.getUsername(),
                user.getEncodedPassword(),
                UserRole.ORGANIZATION_OWNER.name()
        );
        final ObjectMapper objectMapper = new ObjectMapper();
        final ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        final String mappedUser = objectWriter.writeValueAsString(createUserDto);

        mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mappedUser))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.createdAt").isNotEmpty());
    }

    @Test
    @WithMockUser(authorities = {"CASHIER"})
    void createUser_returnForbiddenForNonAdminUser() throws Exception {
        final CreateUserDto createUserDto = new CreateUserDto(
                user.getUsername(),
                user.getEncodedPassword(),
                UserRole.CASHIER.name()
        );
        final ObjectMapper objectMapper = new ObjectMapper();
        final ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        final String mappedUser = objectWriter.writeValueAsString(createUserDto);

        mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mappedUser))
                .andExpect(status().isForbidden());
    }
}