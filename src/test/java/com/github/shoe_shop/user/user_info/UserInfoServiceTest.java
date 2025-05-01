package com.github.shoe_shop.user.user_info;

import com.github.shoe_shop.exceptions.EntityAlreadyExistsException;
import com.github.shoe_shop.user.user.User;
import com.github.shoe_shop.user.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shared.TestContainerWithUserAndInfo;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class UserInfoServiceTest extends TestContainerWithUserAndInfo {

    @Autowired
    private UserInfoService userInfoService;

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
    public void createInfo_returnCreatedInfo_whenDoesntExistForUser() {
        final User createdUser = userRepository.save(user);

        final UserInfo savedUserInfo = userInfoService.createInfo(userInfo, createdUser.getId());

        assertNotNull(savedUserInfo);
        assertEquals(savedUserInfo, userInfo);
        assertNotNull(savedUserInfo.getUser());
        assertNotNull(savedUserInfo.getId());
    }

    @Test
    public void createInfo_throwAlreadyExists_whenUserHasItsInfo() {
        final User createdUser = userRepository.save(user);
        userInfo.setUser(createdUser);
        userInfoRepository.save(userInfo);

        assertThrows(EntityAlreadyExistsException.class, () -> userInfoService.createInfo(userInfo, user.getId()));
    }

    @Test
    public void getInfoById_returnFoundedInfo_ifExists() {
        final User createdUser = userRepository.save(user);
        userInfo.setUser(createdUser);
        final UserInfo savedUserInfo = userInfoRepository.save(userInfo);

        final UserInfo foundedUserInfo = userInfoService.getInfoById(savedUserInfo.getId());

        assertNotNull(foundedUserInfo);
        assertEquals(savedUserInfo, foundedUserInfo);
    }

    @Test
    public void getInfoById_ThrowsNotFoundExceptions_whenNotExists() {
        assertThrows(EntityNotFoundException.class, () -> userInfoService.getInfoById(1L));
    }

    @Test
    public void findByUserUsername_returnFoundedInfo_ifAttachedToUser() {
        final User createdUser = userRepository.save(user);
        userInfo.setUser(createdUser);
        final UserInfo savedInfo = userInfoRepository.save(userInfo);

        final UserInfo foundedUserInfo = userInfoService.findByUserUsername(createdUser.getUsername());

        assertEquals(savedInfo, foundedUserInfo);
    }

    @Test
    public void findByUserUsername_ThrowsNotFoundExceptions_whenNotAttachedToUser() {
        final User createdUser = userRepository.save(user);
        userInfo.setUser(createdUser);
        userInfoRepository.save(userInfo);

        assertThrows(EntityNotFoundException.class, () -> userInfoService.findByUserUsername(user.getUsername() + UUID.randomUUID()));
    }
}