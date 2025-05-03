package com.github.shoe_shop.article;

import com.github.shoe_shop.base.CreateDateAuditableEntity;
import com.github.shoe_shop.user.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "article_numbers")
@EqualsAndHashCode(onlyExplicitlyIncluded = true,callSuper = false)
public class ArticleNumber extends CreateDateAuditableEntity {
    @Id
    @EqualsAndHashCode.Include
    @SequenceGenerator(sequenceName = "article_numbers_seq",name = "article_numbers_seq", initialValue = 1, allocationSize = 15)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "article_numbers_seq")
    private Long id;

    @Column(nullable = false, unique = true)
    private String article;

    @Column(name = "shoe_size", nullable = false, updatable = false)
    private byte shoeSize;

    @Column(name = "shoe_type", nullable = false, updatable = false)
    private String shoeType;

    @Column(name = "brand_name", nullable = false, updatable = false)
    private String brandName;

    @Column(name = "shoe_color", nullable = false, updatable = false)
    private String shoeColor;

    @Column(name = "producer_country", nullable = false, updatable = false)
    private String producerCountry;

    @Column(name = "default_price", nullable = false)
    private BigDecimal defaultPrice;

    @Column(name = "line_name", nullable = false, updatable = false)
    private String lineName;
}
