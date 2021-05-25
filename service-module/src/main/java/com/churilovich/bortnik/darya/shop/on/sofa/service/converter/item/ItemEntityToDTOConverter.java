package com.churilovich.bortnik.darya.shop.on.sofa.service.converter.item;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.Item;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.ItemCategory;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ItemCategoryDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ItemDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ItemEntityToDTOConverter implements Converter<Item, ItemDTO> {
    private final Converter<ItemCategory, ItemCategoryDTO> categoryConverter;

    public ItemEntityToDTOConverter(Converter<ItemCategory, ItemCategoryDTO> categoryConverter) {
        this.categoryConverter = categoryConverter;
    }

    @Override
    public ItemDTO convert(Item item) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(item.getId());
        itemDTO.setName(item.getName());
        itemDTO.setUniqueNumber(item.getUniqueNumber());
        itemDTO.setDescription(item.getDescription());
        itemDTO.setPrice(item.getPrice());
        ItemCategory category = item.getCategory();
        itemDTO.setItemCategoryDTO(categoryConverter.convert(category));
        itemDTO.setUserId(item.getUser().getId());
        return itemDTO;
    }
}
