package com.github.shoe_shop.article;

import com.github.shoe_shop.user.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleNumberRepository extends CrudRepository<ArticleNumber, Long> {
    List<ArticleNumber> findAllByCreatedBy(final User user);

    List<ArticleNumber> findAllByCreatedByUsername(String username);
}
