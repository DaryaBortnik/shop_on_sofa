package com.churilovich.bortnik.darya.shop.on.sofa.service.converter.item;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.Item;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.ItemCategory;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.User;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ItemDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ItemDTOToEntityConverter implements Converter<ItemDTO, Item> {

    @Override
    public Item convert(ItemDTO itemDTO) {
        Item item = new Item();
        item.setId(itemDTO.getId());
        item.setName(itemDTO.getName());
        item.setUnique_name(itemDTO.getUnique_name());
        item.setDescription(itemDTO.getDescription());
        item.setPrice(itemDTO.getPrice());
//        item.setCategory(getConvertedCategory(itemDTO));
//        item.setUser(getConvertedUser(itemDTO));
        return item;
    }

    private ItemCategory getConvertedCategory(ItemDTO itemDTO) {
        ItemCategory category = new ItemCategory();
        category.setName(itemDTO.getCategory());
        return category;
    }

    private User getConvertedUser(ItemDTO itemDTO) {
        User user = new User();
        user.setId(itemDTO.getUserId());
        return user;
    }

}
