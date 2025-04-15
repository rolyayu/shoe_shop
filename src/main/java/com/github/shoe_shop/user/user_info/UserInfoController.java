package com.github.shoe_shop.user.user_info;

import com.github.shoe_shop.user.user_info.dto.CreateUserInfoDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/{userId}/info")
@RequiredArgsConstructor
@Secured("ADMINISTRATOR")
public class UserInfoController {
    private final UserInfoService infoService;

    private final UserInfoDtoMapper dtoMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserInfo createInfo(@Valid @RequestBody final CreateUserInfoDto createDto,
                               @PathVariable("userId") final Long userId) {
        return infoService.createInfo(
                dtoMapper.dtoToEntity(createDto),
                userId
        );
    }

    @GetMapping
    public UserInfo getInfoByUserId(@PathVariable("userId") final Long userId) {
        return infoService.getInfoById(userId);
    }
}
