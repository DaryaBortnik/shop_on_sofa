package com.churilovich.bortnik.darya.shop.on.sofa.service.impl;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.ItemCategoryRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.ItemCategory;
import com.churilovich.bortnik.darya.shop.on.sofa.service.ItemCategoryService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.GetAllServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.GetByParameterServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ItemCategoryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemCategoryServiceImpl implements ItemCategoryService {
    private final ItemCategoryRepository categoryRepository;
    private final ConversionService conversionService;

    @Override
    public ItemCategoryDTO findById(Long id) {
        return categoryRepository.findById(id)
                .map(itemCategory -> conversionService.convert(itemCategory, ItemCategoryDTO.class))
                .orElseThrow(() ->
                        new GetByParameterServiceException("Can't get category with id on service level : id = " + id));
    }

    @Override
    public List<ItemCategoryDTO> findAll() {
        return categoryRepository.findAll().stream()
                .map(this::getConvertedCategory)
                .collect(Collectors.toList());
    }

    private ItemCategoryDTO getConvertedCategory(ItemCategory itemCategory) {
        ItemCategoryDTO categoryDTO = conversionService.convert(itemCategory, ItemCategoryDTO.class);
        return Optional.ofNullable(categoryDTO)
                .orElseThrow(() -> new GetAllServiceException("Can't get all because after converter items are null"));
    }
}
