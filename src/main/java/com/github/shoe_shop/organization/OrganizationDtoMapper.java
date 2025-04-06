package com.github.shoe_shop.organization;

import com.github.shoe_shop.organization.dto.CreateOrganizationDto;
import org.springframework.stereotype.Service;

@Service
public class OrganizationDtoMapper {
    public Organization dtoToEntity(final CreateOrganizationDto createDto) {
        final Organization organization = new Organization();
        organization.setOrganizationName(createDto.name());
        organization.setUnp(createDto.unp());
        organization.setAddress(createDto.address());
        return organization;
    }
}
