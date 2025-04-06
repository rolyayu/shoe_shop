package com.github.shoe_shop.leftovers;

import com.github.shoe_shop.article.ArticleNumber;
import com.github.shoe_shop.base.CreateDateAuditableEntity;
import com.github.shoe_shop.user.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Leftover extends CreateDateAuditableEntity {

    @Id
    @Column(nullable = false, unique = true, updatable = false)
    private String scan;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", name = "accepted_by_id")
    private User user;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", name = "article_number_id")
    private ArticleNumber articleNumber;
}
