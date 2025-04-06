package com.github.shoe_shop.organization.workstations;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkstationRepository extends CrudRepository<Workstation, String> {
    List<Workstation> findAllByOrganizationUnp(final String unp);
}
