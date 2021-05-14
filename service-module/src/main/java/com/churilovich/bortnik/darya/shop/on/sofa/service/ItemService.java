package com.churilovich.bortnik.darya.shop.on.sofa.service;

import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ItemDTO;

import java.util.List;

public interface ItemService {
    List<ItemDTO> findAll();

    ItemDTO findById(Long id);

    void add(ItemDTO itemDTO);

    void deleteById(Long id);
}
