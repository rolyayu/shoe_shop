package com.github.shoe_shop.organization.workstations;

import com.github.shoe_shop.organization.workstations.dto.CreateWorkstationDto;
import org.springframework.stereotype.Service;

@Service
public class WorkstationDtoMapper {
    public Workstation mapToEntity(final CreateWorkstationDto createDto) {
        final Workstation workstation = new Workstation();
        workstation.setIp(createDto.ip());
        return workstation;
    }
}
