package com.github.shoe_shop.user.user_info;

import com.github.shoe_shop.user.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends CrudRepository<UserInfo, Long> {
    Optional<UserInfo> findByUser(final User user);
    Optional<UserInfo> findByUserUsername(final String username);
}
