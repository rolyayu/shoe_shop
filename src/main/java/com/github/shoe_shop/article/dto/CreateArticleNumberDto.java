package com.github.shoe_shop.article.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;

public record CreateArticleNumberDto(
        @JsonProperty("brand_name") @NotNull @NotBlank String brandName,
        @JsonProperty("shoe_size") @NotNull @Range(min = 18, max = 60) byte shoeSize,
        @JsonProperty("shoe_type") @NotNull @NotBlank String shoeType,
        @JsonProperty("shoe_color") @NotNull @Length(min = 3) String shoeColor,
        @JsonProperty("default_price") @NotNull @Min(1) BigDecimal price,
        @JsonProperty("producer_country") @NotNull @NotBlank String producerCountry
) {
}
