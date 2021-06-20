package com.churilovich.bortnik.darya.shop.on.sofa.service.impl;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.CategoryRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.ItemCategory;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ItemCategoryDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private ConversionService conversionService;
    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    public void shouldFindAllCategories() {
        List<ItemCategory> itemCategories = new ArrayList<>();
        ItemCategory itemCategory = new ItemCategory();
        Long id = 1L;
        itemCategory.setId(id);
        itemCategories.add(itemCategory);
        when(categoryRepository.findAll()).thenReturn(itemCategories);
        ItemCategoryDTO itemCategoryDTO = new ItemCategoryDTO();
        itemCategoryDTO.setId(id);
        when(conversionService.convert(itemCategory, ItemCategoryDTO.class)).thenReturn(itemCategoryDTO);
        List<ItemCategoryDTO> itemCategoriesDTO = new ArrayList<>();
        itemCategoriesDTO.add(itemCategoryDTO);

        List<ItemCategoryDTO> actualCategories = categoryService.findAll();

        assertEquals(itemCategoriesDTO.size(), actualCategories.size());
    }

    @Test
    public void shouldReturnEmptyListIfCantFindCategories(){
        List<ItemCategoryDTO> actualCategories = categoryService.findAll();
        assertTrue(actualCategories.isEmpty());
    }
}