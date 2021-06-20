package com.churilovich.bortnik.darya.shop.on.sofa.service.impl;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.CategoryRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.ItemCategory;
import com.churilovich.bortnik.darya.shop.on.sofa.service.CategoryService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.GetAllServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ItemCategoryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ConversionService conversionService;

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
