package com.github.shoe_shop.user.user_info;

import com.github.shoe_shop.organization.Organization;
import com.github.shoe_shop.user.user_info.dto.CreateUserInfoDto;
import org.springframework.stereotype.Service;

@Service
public class UserInfoDtoMapper {
    public UserInfo dtoToEntity(final CreateUserInfoDto createDto) {
        final UserInfo userInfo = new UserInfo();
        userInfo.setFullName(createDto.fullName());
        userInfo.setJobPosition(createDto.jopPos());
//        userInfo.setOrganizationName(createDto.organization());
        final Organization organization = new Organization();
        organization.setUnp(createDto.organizationUnp());
        userInfo.setOrganization(organization);
        return userInfo;
    }
}
