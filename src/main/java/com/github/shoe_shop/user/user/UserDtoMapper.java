package com.github.shoe_shop.user.user;

import com.github.shoe_shop.user.user.dto.CreateUserDto;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper {
    public User createDtoToUser(final CreateUserDto createUserDto) {
        return User.builder().username(createUserDto.username())
                .encodedPassword(createUserDto.password())
                .role(UserRole.CASHIER).build();
    }
}
