package com.github.shoe_shop.article;

import lombok.Getter;

@Getter
public enum ShoeType {
    CROSS("Кроссовки");
    ShoeType(final String translate) {
        this.translate = translate;
    }

    private final String translate;
}
