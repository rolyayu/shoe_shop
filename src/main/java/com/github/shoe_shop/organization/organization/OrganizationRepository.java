package com.github.shoe_shop.organization.organization;

import com.github.shoe_shop.user.user_info.UserInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrganizationRepository extends CrudRepository<Organization, Long> {

    Optional<Organization> findByUnp(final String unp);
    boolean existsByUnp(final String unp);
}
