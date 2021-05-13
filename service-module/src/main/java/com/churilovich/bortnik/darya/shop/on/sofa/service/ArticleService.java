package com.churilovich.bortnik.darya.shop.on.sofa.service;

import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ArticleDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.PageDTO;

import java.util.List;

public interface ArticleService {
    PageDTO<ArticleDTO> getArticlesOnPage(Long currentPageNumber);

    ArticleDTO findById(Long id);

    List<ArticleDTO> findAll();

    void add(ArticleDTO article);

    void deleteById(Long id);

    ArticleDTO getWithComments(Long id);
}
