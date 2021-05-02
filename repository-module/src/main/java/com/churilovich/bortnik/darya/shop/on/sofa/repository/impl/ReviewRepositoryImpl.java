package com.churilovich.bortnik.darya.shop.on.sofa.repository.impl;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.ReviewRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.Review;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.lang.invoke.MethodHandles;
import java.util.List;

@Repository
public class ReviewRepositoryImpl extends GenericRepositoryImpl<Long, Review> implements ReviewRepository {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    @SuppressWarnings("unchecked")
    public List<Review> findAll(Long startNumberOnCurrentPage, long amountOnOnePage) {
        logger.info("Finding all reviews on current page : start review number = [{}]", startNumberOnCurrentPage);
        String queryInStringFormat = "from " + entityClass.getName() + " c";
        Query query = entityManager.createQuery(queryInStringFormat);
        query.setMaxResults(Math.toIntExact(amountOnOnePage));
        query.setFirstResult(Math.toIntExact(startNumberOnCurrentPage));
        return query.getResultList();
    }
}
