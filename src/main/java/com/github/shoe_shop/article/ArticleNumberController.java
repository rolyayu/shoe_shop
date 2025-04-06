package com.github.shoe_shop.article;

import com.github.shoe_shop.article.dto.ArticleNumberDtoMapper;
import com.github.shoe_shop.article.dto.CreateArticleNumberDto;
import com.github.shoe_shop.user.user.User;
import com.github.shoe_shop.web.annotations.CurrentUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/article_numbers")
public class ArticleNumberController {
    private final ArticleNumberService articleNumberService;

    private final ArticleNumberDtoMapper dtoMapper;

    @PostMapping
    public ArticleNumber createArticle(@CurrentUser final User currentUser,
                                       @Valid @RequestBody final CreateArticleNumberDto createArticleNumberDto) {
        return articleNumberService.create(
                currentUser,
                dtoMapper.dtoToEntity(createArticleNumberDto)
        );
    }

    @GetMapping
    public List<ArticleNumber> findArticlesOfCurrentUser(@CurrentUser final User user) {
        return articleNumberService.findAllByUser(user);
    }

    @GetMapping
    @RequestMapping("/by_user")
    @Secured({"ADMIN"})
    public List<ArticleNumber> findArticlesByUsername(@RequestParam final String username) {
        return articleNumberService.findAllyByUsername(username);
    }
}
