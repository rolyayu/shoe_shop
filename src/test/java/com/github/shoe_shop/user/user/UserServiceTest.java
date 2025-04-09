package com.github.shoe_shop.user.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository repository;

    private User user;

    @BeforeEach
    void setUpMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    void setUpUser() {
        user = new User();
        user.setId(System.currentTimeMillis());
        user.setUsername("test");
        user.setRole(UserRole.CASHIER);
    }

    @Test
    void createUser_shouldReturnCreatedUser_whenUsernameNotFound() {

        when(repository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        when(repository.save(user)).thenReturn(user);

        final User userCreated = service.createUser(user);
        assertNotNull(userCreated);
        assertNotNull(userCreated.getUsername());
    }

    @Test
    void createUser_shouldThrowBadCredentialsException_whenUsernameAlreadyExists() {
        when(repository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        assertThrows(BadCredentialsException.class, () -> service.createUser(user));
    }
}