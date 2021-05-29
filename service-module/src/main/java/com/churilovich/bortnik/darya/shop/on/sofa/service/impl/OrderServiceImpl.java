package com.churilovich.bortnik.darya.shop.on.sofa.service.impl;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.OrderRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.exception.GetEntitiesAmountRepositoryException;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.Item;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.Order;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.enums.OrderStatusEnum;
import com.churilovich.bortnik.darya.shop.on.sofa.service.ItemService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.OrderService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.PaginationService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.UserService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.GetByParameterServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.GetOnPageServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.UpdateParameterServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ItemDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.OrderDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserDTOLogin;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.element.PageDTO;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.churilovich.bortnik.darya.shop.on.sofa.service.constants.PaginationValueConstants.AMOUNT_ON_ONE_PAGE;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final OrderRepository orderRepository;
    private final ItemService itemService;
    private final UserService userService;
    private final ConversionService conversionService;
    private final PaginationService<OrderRepository> paginationService;

    @Override
    @Transactional
    public PageDTO<OrderDTO> getOrdersOnPage(Long currentPageNumber) {
        try {
            Long amountOfPages = paginationService.getAmountOfPages(orderRepository);
            return buildPageWithOrders(currentPageNumber, amountOfPages);
        } catch (GetEntitiesAmountRepositoryException e) {
            logger.error(e.getMessage(), e);
            throw new GetOnPageServiceException("Can't get all orders on current page on service level " +
                    "due to impossibility to get total amount of orders", e);
        }
    }

    @Override
    @Transactional
    public OrderDTO findById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new GetByParameterServiceException("Can't get order with id on service level : id = " + id));
        return conversionService.convert(order, OrderDTO.class);
    }

    @Override
    @Transactional
    public OrderDTO updateOrderDetails(OrderDTO orderDTO) {
        Long id = orderDTO.getId();
        Order updatedMergedOrder = orderRepository.findById(id)
                .map(order -> {
                    Order updatedOrder = updateOrder(orderDTO, order);
                    orderRepository.merge(updatedOrder);
                    return updatedOrder;
                })
                .orElseThrow(() -> new UpdateParameterServiceException("Can't update order because can't find it by id"));
        return conversionService.convert(updatedMergedOrder, OrderDTO.class);
    }

    @Override
    public List<OrderDTO> findAll() {
        return orderRepository.findAll().stream()
                .map(order -> conversionService.convert(order, OrderDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderDTO add(Long id, UserDTOLogin userDTOLogin) {
        OrderDTO orderDTO = new OrderDTO();
        ItemDTO item = itemService.findById(id);
        orderDTO.setItem(item);
        orderDTO.setItemAmount(1L);
        orderDTO.setStatus("NEW");
        orderDTO.setPrice(item.getPrice());
        orderDTO.setNumber(id);
        Long userId = userDTOLogin.getUserId();
        UserDTO user = userService.findById(userId);
        orderDTO.setUser(user);
        orderDTO.setDateAdded(LocalDate.now());
        Order order = conversionService.convert(orderDTO, Order.class);
        orderRepository.persist(order);
        return conversionService.convert(order, OrderDTO.class);
    }

    private PageDTO<OrderDTO> buildPageWithOrders(Long currentPageNumber, Long amountOfPages) {
        PageDTO<OrderDTO> page = getPageWithOrders(amountOfPages);
        Long startNumberOnCurrentPage = paginationService.getStartEntityNumberOnCurrentPage(currentPageNumber, amountOfPages, AMOUNT_ON_ONE_PAGE);
        List<Order> orders = orderRepository.findAllOnPage(startNumberOnCurrentPage, AMOUNT_ON_ONE_PAGE);
        addOrdersToPage(page, orders);
        return page;
    }

    private PageDTO<OrderDTO> getPageWithOrders(Long amountOfPages) {
        PageDTO<OrderDTO> page = new PageDTO<>();
        page.setPagesAmount(amountOfPages);
        return page;
    }

    private void addOrdersToPage(PageDTO<OrderDTO> page, List<Order> orders) {
        List<OrderDTO> ordersDTO = getOrders(orders);
        page.getList().addAll(ordersDTO);
    }

    private List<OrderDTO> getOrders(List<Order> orders) {
        return orders.stream()
                .map(order -> conversionService.convert(order, OrderDTO.class))
                .collect(Collectors.toList());
    }

    private Order updateOrder(OrderDTO orderDTO, Order order) {
        String status = orderDTO.getStatus();
        OrderStatusEnum orderStatus = OrderStatusEnum.valueOf(status);
        order.setStatus(orderStatus);
        return order;
    }
}
