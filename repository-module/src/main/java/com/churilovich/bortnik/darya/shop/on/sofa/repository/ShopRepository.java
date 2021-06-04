package com.churilovich.bortnik.darya.shop.on.sofa.repository;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.Shop;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface ShopRepository extends GenericRepository<Long, Shop> {
    List<Shop> findByRoleId(Long roleId);

    Optional<Shop> findByUserId(Long userId);

    List<Shop> findOnPage(Long startNumberOnCurrentPage, long amountOnOnePage);
}

