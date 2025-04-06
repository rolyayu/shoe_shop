package com.github.shoe_shop.web;

import com.github.shoe_shop.web.resolvers.CurrentUserAnnotationResolver;
import com.github.shoe_shop.web.resolvers.RequestIdAnnotationResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final CurrentUserAnnotationResolver currentUserAnnotationResolver;

    private final RequestIdAnnotationResolver requestIdAnnotationResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(currentUserAnnotationResolver);
        resolvers.add(requestIdAnnotationResolver);
    }
}
