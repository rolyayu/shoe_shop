package com.github.shoe_shop.user.user_info;

import com.github.shoe_shop.exceptions.EntityAlreadyExistsException;
import com.github.shoe_shop.user.user.User;
import com.github.shoe_shop.user.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserInfoService {
    private final UserInfoRepository userInfoRepository;

    private final UserService userService;

    @CachePut(cacheNames = "user_info", key = "#userId")
    public UserInfo createInfo(final UserInfo info, final Long userId) {
        final User user = userService.findById(userId);
        Optional<UserInfo> userInfoOpt = userInfoRepository.findByUser(user);
        if (userInfoOpt.isPresent()) {
            throw new EntityAlreadyExistsException("Info already exists for user " + userId);
        }
        info.setUser(user);
        return userInfoRepository.save(info);
    }

    @Cacheable(cacheNames = "user_info", key = "#id")
    public UserInfo getInfoById(final Long id) {
        return userInfoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User info for given id not found"));
    }

    @Cacheable(cacheNames = "user_info", key = "#username")
    public UserInfo findByUserUsername(final String username) {
        return userInfoRepository.findByUserUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User info for given username not found"));
    }
}
