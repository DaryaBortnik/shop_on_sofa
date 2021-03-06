package com.churilovich.bortnik.darya.shop.on.sofa.repository;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.element.Report;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.Item;

import java.util.List;

public interface ItemRepository extends GenericRepository<Long, Item> {
    List<Item> findAllOnPage(Long startNumberOnCurrentPage, long amountOnOnePage);

    List<Report> findAllReportGroupByNameAndPrice(Long userId);

    List<Item> findByCategoryId(Long id);

    List<Item> findAllByUserIdOnPage(Long startNumberOnCurrentPage, long amountOnOnePage, Long id);

    List<Item> getItemsBySaleId(Long userSaleId);
}
