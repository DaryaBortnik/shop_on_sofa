package com.churilovich.bortnik.darya.shop.on.sofa.service.converter.item;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.Item;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.ItemCategory;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ItemCategoryDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ItemDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ItemDTOToEntityConverter implements Converter<ItemDTO, Item> {
    private final Converter<ItemCategoryDTO, ItemCategory> categoryConverter;

    public ItemDTOToEntityConverter(Converter<ItemCategoryDTO, ItemCategory> categoryConverter) {
        this.categoryConverter = categoryConverter;
    }

    @Override
    public Item convert(ItemDTO itemDTO) {
        Item item = new Item();
        item.setId(itemDTO.getId());
        item.setName(itemDTO.getName());
        item.setUniqueNumber(itemDTO.getUniqueNumber());
        item.setDescription(itemDTO.getDescription());
        item.setCategory(categoryConverter.convert(itemDTO.getItemCategoryDTO()));
        item.setPrice(itemDTO.getPrice());
        item.setUniqueNumber(UUID.randomUUID().toString());
        return item;
    }
}
