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

    @Secured({"HEAD_OF_BRANCH", "ORGANIZATION_OWNER"})
    @PostMapping
    public ArticleNumber createArticle(@Valid @RequestBody final CreateArticleNumberDto createArticleNumberDto) {
        return articleNumberService.create(dtoMapper.dtoToEntity(createArticleNumberDto)
        );
    }
}
