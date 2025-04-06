package com.github.shoe_shop.user.user_info;

import com.github.shoe_shop.exceptions.EntityAlreadyExistsException;
import com.github.shoe_shop.organization.Organization;
import com.github.shoe_shop.organization.OrganizationService;
import com.github.shoe_shop.user.user.User;
import com.github.shoe_shop.user.user.UserService;
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

    private final OrganizationService organizationService;

    @CachePut(cacheNames = "user_info", key = "#userId")
    public UserInfo createInfo(final UserInfo info, final Long userId) {
        final User user = userService.findById(userId);
        Optional<UserInfo> userInfoOpt = userInfoRepository.findByUser(user);
        if (userInfoOpt.isPresent()) {
            throw new EntityAlreadyExistsException("Info already exists for user " + userId);
        }
        info.setUser(user);
        Organization organization = organizationService.findByUnp(info.getOrganization().getUnp());
        info.setOrganization(organization);
        return userInfoRepository.save(info);
    }

    @Cacheable(cacheNames = "user_info", key = "#currentUser")
    public UserInfo getInfoByUser(final User currentUser) {
        return userInfoRepository.findByUser(currentUser).orElse(null);
    }

    @Cacheable(cacheNames = "user_info", key = "#id")
    public UserInfo getInfoById(final Long id) {
        return userInfoRepository.findByUserId(id).orElse(null);
    }
}
