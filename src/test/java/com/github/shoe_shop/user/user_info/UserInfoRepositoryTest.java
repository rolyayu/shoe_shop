package com.github.shoe_shop.user.user_info;

import com.github.shoe_shop.user.user.User;
import com.github.shoe_shop.user.user.UserRepository;
import com.github.shoe_shop.user.user.UserRole;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shared.BaseContainerTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserInfoRepositoryTest extends BaseContainerTest {
    private static User user;

    private static UserInfo userInfo;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeAll
    static void setUp() {
        user = new User();
        user.setUsername("ShoeShop");
        user.setEncodedPassword("password");
        user.setRole(UserRole.ORGANIZATION_OWNER);

        userInfo = new UserInfo();
        userInfo.setBirthDate(LocalDate.of(2003,1,27));
        userInfo.setFullName("Shoe Shop");
        userInfo.setGender(Gender.MALE);
    }

    @BeforeEach
    @Transactional
    void setUpDB() {
        userInfoRepository.deleteAll();
        userRepository.deleteAll();
        userInfo.setUser(null);
        userInfo.setId(null);
        user.setId(null);
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