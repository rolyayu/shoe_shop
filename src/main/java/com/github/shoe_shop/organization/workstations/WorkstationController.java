package com.github.shoe_shop.organization.workstations;

import com.github.shoe_shop.base.constraints.IP;
import com.github.shoe_shop.organization.workstations.dto.CreateWorkstationDto;
import com.github.shoe_shop.web.annotations.RequestIP;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/workstations")
@Secured("ADMINISTRATOR")
public class WorkstationController {

    private final WorkstationService service;

    private final WorkstationDtoMapper dtoMapper;


    @PostMapping
    public Workstation createWorkstation(@RequestBody @Valid final CreateWorkstationDto createDto) {
        return service.create(dtoMapper.mapToEntity(createDto), createDto.branchId());
    }

    @PostMapping("/current/branch/{branch_id}")
    public Workstation createWorkstationByRequest(@RequestIP @IP final String ip,
                                                  @Valid @PathVariable("branch_id") @UUID final String branchId) {
        final Workstation workstationToCreate = new Workstation();
        workstationToCreate.setIp(ip);
        return service.create(workstationToCreate, branchId);
    }
}
