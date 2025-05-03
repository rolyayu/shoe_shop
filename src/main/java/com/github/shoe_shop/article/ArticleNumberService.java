package com.github.shoe_shop.article;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleNumberService {
    private final ArticleNumberRepository repository;

    public ArticleNumber create(final ArticleNumber articleNumber) {
        ArticleNumberUtils.buildAndSetArticle(articleNumber);
        return repository.save(articleNumber);
    }
}
