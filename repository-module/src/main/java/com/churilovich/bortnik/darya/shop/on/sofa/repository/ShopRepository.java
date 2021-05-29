package com.churilovich.bortnik.darya.shop.on.sofa.repository;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.Shop;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.User;

import java.util.List;

public interface ShopRepository extends GenericRepository<Long, Shop> {
    List<Shop> findByRoleId(Long roleId);
}

