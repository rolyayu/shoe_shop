package com.github.shoe_shop.organization.workstations;

import com.github.shoe_shop.base.constraints.IP;
import com.github.shoe_shop.base.constraints.UNP;
import com.github.shoe_shop.organization.workstations.dto.CreateWorkstationDto;
import com.github.shoe_shop.web.annotations.RequestIP;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/workstations")
public class WorkstationController {

    private final WorkstationService service;

    private final WorkstationDtoMapper dtoMapper;


    @PostMapping
    public Workstation createWorkstation(@RequestBody @Valid final CreateWorkstationDto createDto) {
        return service.create(dtoMapper.mapToEntity(createDto), createDto.organizationUnp());
    }

    @PostMapping("/current/organization/{unp}")
    public Workstation createWorkstationByRequest(@RequestIP @IP final String ip,
                                                  @Valid @UNP @PathVariable("unp") final String unp) {
        final Workstation workstationToCreate = new Workstation();
        workstationToCreate.setIp(ip);
        return service.create(workstationToCreate, unp);
    }
}
