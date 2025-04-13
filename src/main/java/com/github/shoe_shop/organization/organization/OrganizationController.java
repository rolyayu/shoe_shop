package com.github.shoe_shop.organization.organization;

import com.github.shoe_shop.organization.organization.dto.CreateOrganizationDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/organizations")
public class OrganizationController {
    private final OrganizationService organizationService;

    private final OrganizationDtoMapper dtoMapper;

    @PostMapping
    @Secured("ADMINISTRATOR")
    public Organization createOrganization(@Valid @RequestBody final CreateOrganizationDto createOrganizationDto) {
        return organizationService.createOrganization(dtoMapper.dtoToEntity(createOrganizationDto));
    }

    @GetMapping("/{unp}")
    @Secured("ADMINISTRATOR")
    public Organization findById(@PathVariable("unp") final String unp) {
        return organizationService.findByUnp(unp);
    }
}
