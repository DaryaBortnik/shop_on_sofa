package com.churilovich.bortnik.darya.shop.on.sofa.service.converter.article;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.Article;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ArticleDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ArticleDTOToEntityConverter implements Converter<ArticleDTO, Article> {

    @Override
    public Article convert(ArticleDTO articleDTO) {
        Article article = new Article();
        article.setId(articleDTO.getId());
        article.setName(articleDTO.getName());
        article.setShortDescription(articleDTO.getShortDescription());
        article.setFullDescription(articleDTO.getFullDescription());
        article.setDateAdded(articleDTO.getDateAdded());
        return article;
    }
}
