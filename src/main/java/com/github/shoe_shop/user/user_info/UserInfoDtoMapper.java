package com.github.shoe_shop.user.user_info;

import com.github.shoe_shop.user.user_info.dto.CreateUserInfoDto;
import org.springframework.stereotype.Service;

@Service
public class UserInfoDtoMapper {
    public UserInfo dtoToEntity(final CreateUserInfoDto createDto) {
        final UserInfo userInfo = new UserInfo();
        userInfo.setFullName(createDto.fullName());
        userInfo.setGender(Gender.valueOf(createDto.gender()));
        userInfo.setBirthDate(createDto.birthDate());
        return userInfo;
    }
}
