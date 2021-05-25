package com.churilovich.bortnik.darya.shop.on.sofa.repository;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.Review;

import java.util.List;

public interface ReviewRepository extends GenericRepository<Long, Review> {
    List<Review> findAll(Long offset, long amountOnOnePage);
}
