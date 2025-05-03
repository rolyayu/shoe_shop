package com.github.shoe_shop.article;

import java.util.Locale;

public class ArticleNumberUtils {
    public static String buildArticle(final ArticleNumber articleNumber) {
        final StringBuilder article = new StringBuilder();
        article.append(articleNumber.getBrandName().toUpperCase(Locale.ROOT), 0, 3);
        article.append('/');
        article.append(articleNumber.getLineName().toUpperCase(Locale.ROOT), 0, 3);
        article.append('-');
        article.append(articleNumber.getProducerCountry().toUpperCase(Locale.ROOT), 0, 3);
        article.append('-');
        article.append(articleNumber.getShoeType().toUpperCase(Locale.ROOT), 0, 2);
        article.append(articleNumber.getShoeSize());
        article.append(articleNumber.getShoeColor().toUpperCase(Locale.ROOT), 0, 3);
        return article.toString();
    }

    public static void buildAndSetArticle(final ArticleNumber articleNumber) {
        final String article = buildArticle(articleNumber);
        articleNumber.setArticle(article);
    }
}
