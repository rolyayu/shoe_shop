package com.github.shoe_shop.security;

import com.github.shoe_shop.user.user.User;
import com.github.shoe_shop.user.user.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JpaUserDetailService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(@NonNull final String username) throws UsernameNotFoundException {
        final User foundedUser = userService.findByUsername(username);
        return new SecurityUser(foundedUser);
    }
}
