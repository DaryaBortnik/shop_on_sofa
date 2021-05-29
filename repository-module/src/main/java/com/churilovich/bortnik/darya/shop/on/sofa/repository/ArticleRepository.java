package com.churilovich.bortnik.darya.shop.on.sofa.repository;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.Article;

import java.util.List;

public interface ArticleRepository extends GenericRepository<Long, Article> {
    List<Article> findOnPage(Long startNumberOnCurrentPage, long amountOnOnePage);

    List<Article> findOnPageByUserId(Long startNumberOnCurrentPage, long amountOnOnePage, Long id);

    List<Article> findLatest();

    List<Article> findLatestBySaleUserId(Long userId);

    List<Article> getByUserSaleId(Long userSaleId);
}
