package com.github.shoe_shop.organization.organization;

import com.github.shoe_shop.organization.organization.dto.CreateOrganizationDto;
import com.github.shoe_shop.user.user_info.UserInfo;
import org.springframework.stereotype.Service;

@Service
public class OrganizationDtoMapper {
    public Organization dtoToEntity(final CreateOrganizationDto createDto) {
        final Organization organization = new Organization();
        organization.setOrganizationName(createDto.name());
        organization.setUnp(createDto.unp());
        organization.setAddress(createDto.address());

        final UserInfo userInfo = new UserInfo();
        userInfo.setId(createDto.headInfoId());
        organization.setOrganizationHead(userInfo);

        return organization;
    }
}
