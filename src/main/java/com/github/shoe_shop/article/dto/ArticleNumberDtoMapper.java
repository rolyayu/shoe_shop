package com.github.shoe_shop.article.dto;

import com.github.shoe_shop.article.ArticleNumber;
import com.github.shoe_shop.article.ShoeType;
import org.springframework.stereotype.Component;

@Component
public class ArticleNumberDtoMapper {
    public ArticleNumber dtoToEntity(final CreateArticleNumberDto createDto) {
        return ArticleNumber.builder()
                .brandName(createDto.brandName())
                .defaultPrice(createDto.price())
                .shoeColor(createDto.shoeColor())
                .shoeSize(createDto.shoeSize())
                .shoeType(createDto.shoeType())
                .build();
    }
}
