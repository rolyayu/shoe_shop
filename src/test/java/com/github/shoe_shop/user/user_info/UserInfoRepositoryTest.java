package com.github.shoe_shop.user.user_info;

import com.github.shoe_shop.user.user.User;
import com.github.shoe_shop.user.user.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shared.TestContainerWithUserAndInfo;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserInfoRepositoryTest extends TestContainerWithUserAndInfo {

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
    void findByUser_shouldReturnUserInfo_whenUserAttached() {
        final User savedUser = userRepository.save(user);
        userInfo.setUser(savedUser);
        final UserInfo savedInfo = userInfoRepository.save(userInfo);

        final Optional<UserInfo> founded = userInfoRepository.findByUser(savedUser);
        assertTrue(founded.isPresent());

        assertEquals(savedInfo, founded.get());
    }

    @Test
    void findByUserUsername_shouldReturnUserInfo_whenUserAttached() {
        final User savedUser = userRepository.save(user);
        userInfo.setUser(savedUser);
        final UserInfo savedInfo = userInfoRepository.save(userInfo);

        final Optional<UserInfo> founded = userInfoRepository.findByUserUsername(savedUser.getUsername());
        assertTrue(founded.isPresent());

        assertEquals(savedInfo, founded.get());
    }
}