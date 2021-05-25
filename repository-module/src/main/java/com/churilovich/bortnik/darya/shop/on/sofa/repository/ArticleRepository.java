package com.churilovich.bortnik.darya.shop.on.sofa.repository;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.Article;

import java.util.List;

public interface ArticleRepository extends GenericRepository<Long, Article> {
    List<Article> findAll(Long startNumberOnCurrentPage, long amountOnOnePage);
}
