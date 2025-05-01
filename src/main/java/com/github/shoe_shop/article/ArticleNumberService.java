package com.github.shoe_shop.article;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class ArticleNumberService {
    private final ArticleNumberRepository repository;

    public ArticleNumber create(final ArticleNumber articleNumber) {
        articleNumber.setArticle(buildArticle(articleNumber));
        return repository.save(articleNumber);
    }

    public String buildArticle(final ArticleNumber articleNumber) {
        final StringBuilder article = new StringBuilder();
        article.append(articleNumber.getBrandName().toUpperCase(Locale.ROOT), 0, 3);
        article.append('-');
        article.append(articleNumber.getProducerCountry().toUpperCase(Locale.ROOT), 0, 3);
        article.append('-');
        article.append(articleNumber.getShoeType().toUpperCase(Locale.ROOT), 0, 2);
        article.append(articleNumber.getShoeSize());
        article.append(articleNumber.getShoeColor().toUpperCase(Locale.ROOT), 0, 3);
        return article.toString();
    }
}
