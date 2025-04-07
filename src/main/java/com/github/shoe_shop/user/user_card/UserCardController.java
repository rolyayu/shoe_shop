package com.github.shoe_shop.user.user_card;

import com.github.shoe_shop.user.user_card.dto.CreateCardDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user_cards")
@Secured("ADMINISTRATOR")
public class UserCardController {
    private final UserCardDtoMapper dtoMapper;

    private final UserCardService cardService;

    @PostMapping
    public UserCard createCard(@Valid @RequestBody final CreateCardDto createCardDto) {
        return cardService.createCard(dtoMapper.mapCreateDtoToEntity(createCardDto));
    }
}
