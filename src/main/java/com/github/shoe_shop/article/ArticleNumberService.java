package com.github.shoe_shop.article;

import com.github.shoe_shop.user.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleNumberService {
    private final ArticleNumberRepository repository;

    public ArticleNumber create(User currentUser, ArticleNumber articleNumber) {
        articleNumber.setCreatedBy(currentUser);
        return repository.save(articleNumber);
    }

    public List<ArticleNumber> findAllByUser(User user) {
        return repository.findAllByCreatedBy(user);
    }

    public List<ArticleNumber> findAllyByUsername(final String username) {
        return repository.findAllByCreatedByUsername(username);
    }
}
