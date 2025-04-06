package com.github.shoe_shop.article.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;

public record CreateArticleNumberDto(
        @NotBlank String brandName,
        @Range(min = 18, max = 60) byte shoeSize,
        @NotBlank String shoeType,
        @Length(min = 3) String shoeColor,
        @Min(0) BigDecimal price
) {
}
