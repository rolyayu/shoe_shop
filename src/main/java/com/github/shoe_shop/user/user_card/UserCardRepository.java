package com.github.shoe_shop.user.user_card;

import com.github.shoe_shop.organization.Organization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCardRepository extends CrudRepository<UserCard, String> {

    List<UserCard> findAllBySupposedOrganization(final Organization organization);

}
