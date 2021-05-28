package com.churilovich.bortnik.darya.shop.on.sofa.service.converter.order;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.Item;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.Order;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.User;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.enums.OrderStatusEnum;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ItemDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.OrderDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderDTOToEntityConverter implements Converter<OrderDTO, Order> {
    private final Converter<ItemDTO, Item> itemConverter;
    private final Converter<UserDTO, User> userConverter;

    @Override
    public Order convert(OrderDTO orderDTO) {
        Order order = new Order();
        order.setId(orderDTO.getId());
        order.setItem(getConvertedItem(orderDTO));
        order.setItemAmount(orderDTO.getItemAmount());
        order.setPrice(orderDTO.getPrice());
        order.setNumber(orderDTO.getNumber());
        order.setUser(getConvertedUser(orderDTO));
        order.setDateAdded(orderDTO.getDateAdded());
        order.setStatus(getConvertedStatus(orderDTO));
        return order;
    }

    private OrderStatusEnum getConvertedStatus(OrderDTO orderDTO) {
        return OrderStatusEnum.valueOf(orderDTO.getStatus());
    }

    private Item getConvertedItem(OrderDTO orderDTO) {
        ItemDTO item = orderDTO.getItem();
        return itemConverter.convert(item);
    }

    private User getConvertedUser(OrderDTO orderDTO) {
        UserDTO user = orderDTO.getUser();
        return userConverter.convert(user);
    }
}
