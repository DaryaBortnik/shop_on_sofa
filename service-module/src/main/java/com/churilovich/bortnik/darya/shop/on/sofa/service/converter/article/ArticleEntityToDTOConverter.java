package com.churilovich.bortnik.darya.shop.on.sofa.service.converter.article;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.Article;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.User;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ArticleDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArticleEntityToDTOConverter implements Converter<Article, ArticleDTO> {
    private final Converter<User, UserDTO> userConverter;

    @Override
    public ArticleDTO convert(Article article) {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(article.getId());
        articleDTO.setName(article.getName());
        articleDTO.setShortDescription(article.getShortDescription());
        articleDTO.setFullDescription(article.getFullDescription());
        articleDTO.setDateAdded(article.getDateAdded());
        articleDTO.setUser(getConvertedUser(article));
        return articleDTO;
    }

    private UserDTO getConvertedUser(Article article) {
        User user = article.getUser();
        return userConverter.convert(user);
    }
}
