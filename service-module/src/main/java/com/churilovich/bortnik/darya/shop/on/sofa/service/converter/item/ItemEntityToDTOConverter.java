package com.churilovich.bortnik.darya.shop.on.sofa.service.converter.item;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.Item;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ItemDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ItemEntityToDTOConverter implements Converter<Item, ItemDTO> {

    @Override
    public ItemDTO convert(Item item) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(item.getId());
        itemDTO.setName(item.getName());
        itemDTO.setUnique_name(item.getUnique_name());
        itemDTO.setDescription(item.getDescription());
        itemDTO.setPrice(item.getPrice());
        itemDTO.setCategory(item.getCategory().getName());
        itemDTO.setUserId(item.getUser().getId());
        return itemDTO;
    }
}
