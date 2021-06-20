package com.churilovich.bortnik.darya.shop.on.sofa.service.converter.order;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.Item;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.Order;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.User;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ItemDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.OrderDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEntityToDTOConverter implements Converter<Order, OrderDTO> {
    private final Converter<Item, ItemDTO> itemConverter;
    private final Converter<User, UserDTO> userConverter;

    @Override
    public OrderDTO convert(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setItem(getConvertedItem(order));
        orderDTO.setItemAmount(order.getItemAmount());
        orderDTO.setNumber(order.getNumber());
        orderDTO.setPrice(order.getPrice());
        orderDTO.setStatus(order.getStatus().name());
        orderDTO.setUser(getConvertedUser(order));
        return orderDTO;
    }

    private ItemDTO getConvertedItem(Order order) {
        Item item = order.getItem();
        Long userId = order.getItem().getUser().getId();
        ItemDTO itemDTO = itemConverter.convert(item);
        itemDTO.setUserId(userId);
        return itemDTO;
    }


    private UserDTO getConvertedUser(Order order) {
        User user = order.getUser();
        return userConverter.convert(user);
    }
}
