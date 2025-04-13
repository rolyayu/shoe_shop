package com.github.shoe_shop.organization.branch;

import com.github.shoe_shop.user.user_info.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BranchRepository extends CrudRepository<Branch, String> {
    Optional<Branch> findByOrganizationUnpAndBranchNo(String unp, String branchNo);

    Optional<Branch> findByBranchHead(final UserInfo head);

    Optional<Branch> findByBranchId(final String branchId);
}
