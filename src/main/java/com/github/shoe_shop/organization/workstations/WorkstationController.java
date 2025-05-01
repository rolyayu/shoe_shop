package com.github.shoe_shop.organization.workstations;

import com.github.shoe_shop.base.constraints.IP;
import com.github.shoe_shop.web.annotations.RequestIP;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/workstations")
@Secured("ADMINISTRATOR")
public class WorkstationController {

    private final WorkstationService service;


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/ip/{ip}/branch/{branch_id}")
    public Workstation createWorkstation(@PathVariable @Valid @IP String ip, @PathVariable("branch_id") @Valid @UUID String branchId) {
        final Workstation workstationToCreate = new Workstation();
        workstationToCreate.setIp(ip);
        return service.create(workstationToCreate, branchId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/current_ip/branch/{branch_id}")
    public Workstation createWorkstationByRequest(@Valid @RequestIP @IP final String ip,
                                                  @Valid @PathVariable("branch_id") @UUID final String branchId) {
        final Workstation workstationToCreate = new Workstation();
        workstationToCreate.setIp(ip);
        return service.create(workstationToCreate, branchId);
    }
}
