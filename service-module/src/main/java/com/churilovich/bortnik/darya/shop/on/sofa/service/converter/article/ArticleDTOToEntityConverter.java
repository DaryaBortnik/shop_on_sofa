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
public class ArticleDTOToEntityConverter implements Converter<ArticleDTO, Article> {
    private final Converter<UserDTO, User> userConverter;

    @Override
    public Article convert(ArticleDTO articleDTO) {
        Article article = new Article();
        article.setId(articleDTO.getId());
        article.setName(articleDTO.getName());
        article.setShortDescription(articleDTO.getShortDescription());
        article.setFullDescription(articleDTO.getFullDescription());
        article.setDateAdded(articleDTO.getDateAdded());
        article.setUser(getConvertedUser(articleDTO));
        return article;
    }

    private User getConvertedUser(ArticleDTO articleDTO) {
        UserDTO userDTO = articleDTO.getUser();
        return userConverter.convert(userDTO);
    }
}
