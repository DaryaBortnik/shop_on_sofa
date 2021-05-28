package com.churilovich.bortnik.darya.shop.on.sofa.service;

import com.churilovich.bortnik.darya.shop.on.sofa.service.model.OrderDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserDTOLogin;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.element.PageDTO;

import java.util.List;

public interface OrderService {
    PageDTO<OrderDTO> getOrdersOnPage(Long currentPageNumber);

    OrderDTO findById(Long id);

    OrderDTO updateOrderDetails(OrderDTO order);

    List<OrderDTO> findAll();

    OrderDTO add(Long id, UserDTOLogin userDTOLogin);
}
