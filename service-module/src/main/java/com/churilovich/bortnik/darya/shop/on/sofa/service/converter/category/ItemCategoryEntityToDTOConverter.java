package com.churilovich.bortnik.darya.shop.on.sofa.service.converter.category;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.ItemCategory;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ItemCategoryDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ItemCategoryEntityToDTOConverter implements Converter<ItemCategory, ItemCategoryDTO> {

    @Override
    public ItemCategoryDTO convert(ItemCategory category) {
        ItemCategoryDTO categoryDTO = new ItemCategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        return categoryDTO;
    }
}
