package com.github.shoe_shop.article;

public enum ShoeType {
    CROSS("Кроссовки");
    ShoeType(final String translate) {
        this.translate = translate;
    }

    private String translate;
}
