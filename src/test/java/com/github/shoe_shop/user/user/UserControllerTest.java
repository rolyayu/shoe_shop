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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {"application.create-users-on-bootstrap=false"})
@AutoConfigureMockMvc()
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private PasswordEncoder encoder;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private UserDtoMapper userDtoMapper;

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
                UserRole.CASHIER.name()
        );
        final ObjectMapper objectMapper = new ObjectMapper();
        final ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        final String mappedUser = objectWriter.writeValueAsString(createUserDto);

        when(encoder.encode(user.getEncodedPassword())).thenReturn(user.getEncodedPassword());
        when(userDtoMapper.createDtoToUser(createUserDto)).thenReturn(user);
        when(userService.createUser(user)).thenReturn(user);

        mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mappedUser))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(user.getUsername()));
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