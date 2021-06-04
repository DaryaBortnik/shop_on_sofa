package com.churilovich.bortnik.darya.shop.on.sofa.service.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderDTO {
    private Long id;
    private String number;
    private String status;
    private ItemDTO item;
    private Long itemAmount;
    private BigDecimal price;
    private LocalDateTime dateAdded;
    private UserDTO user;
}
