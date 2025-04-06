package com.github.shoe_shop.security.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.shoe_shop.auth.dto.AuthDto;
import com.github.shoe_shop.base.ValidatorUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class LoginAuthenticationConverter implements AuthenticationConverter {

    final private ValidatorUtils validatorUtils;

    @SneakyThrows
    @Override
    public Authentication convert(HttpServletRequest request) {
        final ObjectMapper mapper = new ObjectMapper();
        final AuthDto authDto = mapper.readValue(request.getReader(), AuthDto.class);

        final String validationResult = validatorUtils.getValidationError(AuthDto.class,authDto);

        if (StringUtils.hasLength(validationResult)) {
            throw new BadCredentialsException(validationResult);
        }
        final UserDetails details = User.builder()
                .username(authDto.username())
                .password(authDto.rawPassword())
                .build();
        return new LoginAuthenticationToken(details);
    }
}
