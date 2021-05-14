package com.churilovich.bortnik.darya.shop.on.sofa.repository;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.Item;

import java.util.List;

public interface ItemRepository extends GenericRepository<Long, Item> {
    List<Item> findAllOnPage(Long startNumberOnCurrentPage, long amountOnOnePage);
}
