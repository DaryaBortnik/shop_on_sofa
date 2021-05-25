package com.churilovich.bortnik.darya.shop.on.sofa.repository.impl;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.ReviewRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.Review;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class ReviewRepositoryImpl extends GenericRepositoryImpl<Long, Review> implements ReviewRepository {

    @Override
    @SuppressWarnings("unchecked")
    public List<Review> findAll(Long startNumberOnCurrentPage, long amountOnOnePage) {
        String queryInStringFormat = "from " + entityClass.getName();
        Query query = entityManager.createQuery(queryInStringFormat);
        query.setMaxResults(Math.toIntExact(amountOnOnePage));
        query.setFirstResult(Math.toIntExact(startNumberOnCurrentPage));
        return query.getResultList();
    }
}
