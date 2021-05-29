package com.churilovich.bortnik.darya.shop.on.sofa.repository.impl;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.ArticleRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.Article;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class ArticleRepositoryImpl extends GenericRepositoryImpl<Long, Article> implements ArticleRepository {

    @Override
    @SuppressWarnings("unchecked")
    public List<Article> findOnPage(Long startNumberOnCurrentPage, long amountOnOnePage) {
        String queryInStringFormat = "from " + entityClass.getName() + " order by dateAdded desc";
        Query query = entityManager.createQuery(queryInStringFormat);
        query.setMaxResults(Math.toIntExact(amountOnOnePage));
        query.setFirstResult(Math.toIntExact(startNumberOnCurrentPage));
        return query.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Article> findOnPageByUserId(Long startNumberOnCurrentPage, long amountOnOnePage, Long id) {
        String queryInStringFormat = "from " + entityClass.getName() + " where user_id=:id order by dateAdded desc";
        Query query = entityManager.createQuery(queryInStringFormat);
        query.setParameter("id", id);
        query.setMaxResults(Math.toIntExact(amountOnOnePage));
        query.setFirstResult(Math.toIntExact(startNumberOnCurrentPage));
        return query.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Article> findLatest() {
        String queryInStringFormat = "from " + entityClass.getName() + " order by dateAdded desc";
        Query query = entityManager.createQuery(queryInStringFormat);
        query.setMaxResults(2);
        return query.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Article> findLatestBySaleUserId(Long userId) {
        String queryInStringFormat = "from " + entityClass.getName() + " where user_id=:id order by dateAdded desc";
        Query query = entityManager.createQuery(queryInStringFormat);
        query.setParameter("id", userId);
        query.setMaxResults(1);
        return query.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Article> getByUserSaleId(Long userSaleId) {
        String queryInStringFormat = "from " + entityClass.getName() + " where user_id=:id order by dateAdded desc";
        Query query = entityManager.createQuery(queryInStringFormat);
        query.setParameter("id", userSaleId);
        query.setMaxResults(1);
        return query.getResultList();
    }
}
