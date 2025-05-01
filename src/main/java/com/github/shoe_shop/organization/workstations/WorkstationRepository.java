package com.github.shoe_shop.organization.workstations;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkstationRepository extends CrudRepository<Workstation, Long> {
    boolean existsByIp(final String Ip);
}
