package com.churilovich.bortnik.darya.shop.on.sofa.service;

import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ItemCategoryDTO;

import java.util.List;

public interface ItemCategoryService {
    ItemCategoryDTO findById(Long id);

    List<ItemCategoryDTO> findAll();
}
