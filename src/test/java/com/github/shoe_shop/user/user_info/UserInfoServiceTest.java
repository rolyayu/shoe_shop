package com.github.shoe_shop.user.user_info;

import com.github.shoe_shop.exceptions.EntityAlreadyExistsException;
import com.github.shoe_shop.user.user.User;
import com.github.shoe_shop.user.user.UserRepository;
import com.github.shoe_shop.user.user.UserRole;
import com.github.shoe_shop.user.user.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import shared.BaseContainerTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserInfoServiceTest  {
    private static User user;

    private static UserInfo userInfo;

    @InjectMocks
    private UserInfoService userInfoService;

    @Mock
    private UserService userService;

    @Mock
    private UserInfoRepository userInfoRepository;

    @BeforeEach
    void setUpMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeAll
    static void setUpUsers() {
        user = new User();
        user.setId(1L);
        user.setUsername("ShoeShop");
        user.setEncodedPassword("password");
        user.setRole(UserRole.ORGANIZATION_OWNER);

        userInfo = new UserInfo();
        userInfo.setBirthDate(LocalDate.of(2003,1,27));
        userInfo.setFullName("Shoe Shop");
        userInfo.setGender(Gender.MALE);
    }

    @Test
    public void createInfo_returnCreatedInfo_whenDoesntExistForUser() {
        when(userService.findById(Mockito.anyLong())).thenReturn(user);
        when(userInfoRepository.findByUser(user)).thenReturn(Optional.empty());
        when(userInfoRepository.save(userInfo)).thenReturn(userInfo);

        final UserInfo savedUserInfo = userInfoService.createInfo(userInfo, user.getId());

        assertNotNull(savedUserInfo);
        assertEquals(savedUserInfo, userInfo);
        assertNotNull(savedUserInfo.getUser());
    }

    @Test
    public void createInfo_throwAlreadyExists_whenUserHasItsInfo() {
        when(userService.findById(Mockito.anyLong())).thenReturn(user);
        when(userInfoRepository.findByUser(user)).thenReturn(Optional.of(userInfo));

        assertThrows(EntityAlreadyExistsException.class, () -> userInfoService.createInfo(userInfo, user.getId()));
    }
}