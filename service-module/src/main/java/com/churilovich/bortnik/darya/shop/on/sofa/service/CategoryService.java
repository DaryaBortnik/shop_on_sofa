package com.churilovich.bortnik.darya.shop.on.sofa.service;

import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ItemCategoryDTO;

import java.util.List;

public interface CategoryService {
    List<ItemCategoryDTO> findAll();
}
