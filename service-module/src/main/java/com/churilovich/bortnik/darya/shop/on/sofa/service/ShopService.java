package com.churilovich.bortnik.darya.shop.on.sofa.service;

import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ShopDTO;

import java.util.List;

public interface ShopService {
    List<ShopDTO> findAllShops();

    ShopDTO findById(Long id);
}
