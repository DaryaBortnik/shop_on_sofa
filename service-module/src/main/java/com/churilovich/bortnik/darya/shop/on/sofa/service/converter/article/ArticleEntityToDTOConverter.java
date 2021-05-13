package com.churilovich.bortnik.darya.shop.on.sofa.service.converter.article;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.Article;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.UserProfile;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ArticleDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ArticleEntityToDTOConverter implements Converter<Article, ArticleDTO> {

    @Override
    public ArticleDTO convert(Article article) {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(article.getId());
        articleDTO.setName(article.getName());
        articleDTO.setShortDescription(article.getShortDescription());
        articleDTO.setFullDescription(article.getFullDescription());
        articleDTO.setDateAdded(article.getDateAdded());
        UserProfile userProfile = article.getUser().getUserProfile();
        articleDTO.setUserLastName(userProfile.getLastName());
        articleDTO.setUserFirstName(userProfile.getFirstName());
        return articleDTO;
    }
}
