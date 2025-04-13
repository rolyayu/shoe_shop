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
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Container
    private final static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:16-alpine");
    @Autowired
    private MockMvc mvc;

    private User user;

    @DynamicPropertySource
    public static void configure(DynamicPropertyRegistry registry) {
        System.out.println(container.getJdbcUrl());
        registry.add("spring.datasource.driver-class-name", container::getDriverClassName);
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "validate");
        registry.add("spring.liquibase.driver-class-name", container::getDriverClassName);
        registry.add("spring.liquibase.url", container::getJdbcUrl);
        registry.add("spring.liquibase.user", container::getUsername);
        registry.add("spring.liquibase.password", container::getPassword);
        registry.add("spring.jpa.database-platform", () -> "org.hibernate.dialect.PostgreSQLDialect");
        registry.add("spring.liquibase.change-log", () -> "db/changelog/master-changelog.xml");
        registry.add("spring.liquibase.enabled", () -> true);
        registry.add("application.create-users-on-bootstrap", () -> false);
        registry.add("spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation", () -> true);
    }

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