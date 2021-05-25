package com.churilovich.bortnik.darya.shop.on.sofa.service.converter.category;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.ItemCategory;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ItemCategoryDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ItemCategoryDTOToEntityConverter implements Converter<ItemCategoryDTO, ItemCategory> {

    @Override
    public ItemCategory convert(ItemCategoryDTO categoryDTO) {
        ItemCategory category = new ItemCategory();
        category.setId(categoryDTO.getId());
        category.setName(categoryDTO.getName());
        return category;
    }
}
