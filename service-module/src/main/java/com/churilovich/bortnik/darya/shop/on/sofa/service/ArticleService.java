package com.churilovich.bortnik.darya.shop.on.sofa.service;

import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ArticleDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserDTOLogin;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.element.PageDTO;

import java.util.List;

public interface ArticleService {
    PageDTO<ArticleDTO> getArticlesOnPage(Long currentPageNumber);

    ArticleDTO findById(Long id);

    List<ArticleDTO> findAll();

    Long deleteById(Long id);

    ArticleDTO getWithComments(Long id);

    ArticleDTO add(ArticleDTO article, UserDTOLogin userDTOLogin);

    ArticleDTO updateArticle(ArticleDTO article);
}
