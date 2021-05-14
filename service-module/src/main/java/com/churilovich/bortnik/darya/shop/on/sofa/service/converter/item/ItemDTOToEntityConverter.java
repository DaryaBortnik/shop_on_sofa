package com.churilovich.bortnik.darya.shop.on.sofa.service.converter.item;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.Item;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ItemDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ItemDTOToEntityConverter implements Converter<ItemDTO, Item> {

    @Override
    public Item convert(ItemDTO itemDTO) {
        Item item = new Item();
        item.setId(itemDTO.getId());
        item.setName(itemDTO.getName());
        item.setUniqueNumber(itemDTO.getUniqueNumber());
        item.setDescription(itemDTO.getDescription());
        item.setPrice(itemDTO.getPrice());
        return item;
    }
}
