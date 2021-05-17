package com.churilovich.bortnik.darya.shop.on.sofa.service.impl;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.CategoryRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.service.CategoryService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ItemCategoryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ConversionService conversionService;

    @Override
    public List<ItemCategoryDTO> findAll() {
        return categoryRepository.findAll().stream()
                .map(itemCategory -> conversionService.convert(itemCategory, ItemCategoryDTO.class))
                .collect(Collectors.toList());
    }
}
