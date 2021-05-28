package com.churilovich.bortnik.darya.shop.on.sofa.repository;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.Order;

import java.util.List;

public interface OrderRepository extends GenericRepository<Long, Order> {
    List<Order> findAllOnPage(Long startNumberOnCurrentPage, long amountOnOnePage);
}