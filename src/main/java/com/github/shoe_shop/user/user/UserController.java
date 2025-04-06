package com.github.shoe_shop.user.user;

import com.github.shoe_shop.user.user.dto.CreateUserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    private final UserDtoMapper dtoMapper;

    private final PasswordEncoder encoder;

    @PostMapping
    @Secured("ADMINISTRATOR")
    public User createUser(@RequestBody @Valid final CreateUserDto createUserDto) {
        final User userToCreate = dtoMapper.createDtoToUser(createUserDto);
        userToCreate.setEncodedPassword(encoder.encode(createUserDto.password()));
        return userService.createUser(userToCreate);
    }
}
