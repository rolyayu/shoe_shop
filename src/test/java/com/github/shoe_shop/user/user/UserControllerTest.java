package com.github.shoe_shop.user.user;

import com.github.shoe_shop.security.config.AuthenticationManagerConfig;
import com.github.shoe_shop.security.config.FilterConfig;
import com.github.shoe_shop.security.config.SecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
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
    }

    @Test
    @WithMockUser(roles = {"ADMINISTRATOR"})
    void createUser() {
        when(encoder.encode("admin")).thenReturn("admin");
    }
}