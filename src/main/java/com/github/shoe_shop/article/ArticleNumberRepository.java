package com.github.shoe_shop.article;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleNumberRepository extends CrudRepository<ArticleNumber, Long> {
}
