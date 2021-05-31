package com.churilovich.bortnik.darya.shop.on.sofa.service;

import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ShopDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.element.PageDTO;

import java.util.List;

public interface ShopService {
    List<ShopDTO> findAllShops();

    ShopDTO findById(Long id);

    ShopDTO findBySaleUserId(Long userId);

    ShopDTO updateShopProfileParameters(Long userId, ShopDTO shop);

    void add(ShopDTO shop);

    PageDTO<ShopDTO> getShopsOnPage(Long currentPageNumber);
}
