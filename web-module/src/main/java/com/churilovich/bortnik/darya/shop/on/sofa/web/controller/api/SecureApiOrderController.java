package com.churilovich.bortnik.darya.shop.on.sofa.web.controller.api;

import com.churilovich.bortnik.darya.shop.on.sofa.service.OrderService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.OrderDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class SecureApiOrderController {
    private final OrderService orderService;

    @GetMapping("/orders")
    public ResponseEntity<List<OrderDTO>> getOrders() {
        List<OrderDTO> orders = orderService.findAll();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
        OrderDTO order = orderService.findById(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
