package com.churilovich.bortnik.darya.shop.on.sofa.service.impl;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.ItemCategoryRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.Item;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.ItemCategory;
import com.churilovich.bortnik.darya.shop.on.sofa.service.ItemCategoryService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.GetByParameterServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ItemCategoryDTO;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class ItemCategoryServiceImpl implements ItemCategoryService {
    private final ItemCategoryRepository itemCategoryRepository;
    private final ConversionService conversionService;

    public ItemCategoryServiceImpl(ItemCategoryRepository itemCategoryRepository, ConversionService conversionService) {
        this.itemCategoryRepository = itemCategoryRepository;
        this.conversionService = conversionService;
    }

    @Override
    public ItemCategoryDTO findById(Long id) {
        ItemCategory itemCategory = getItemCategory(id);
        return conversionService.convert(itemCategory, ItemCategoryDTO.class);
    }

    private ItemCategory getItemCategory(Long id) {
        return itemCategoryRepository.findById(id)
                .orElseThrow(() ->
                        new GetByParameterServiceException("Can't get category with id on service level : id = " + id));
    }
}
