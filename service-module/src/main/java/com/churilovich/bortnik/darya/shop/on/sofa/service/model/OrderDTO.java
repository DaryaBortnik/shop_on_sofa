package com.churilovich.bortnik.darya.shop.on.sofa.service.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class OrderDTO {
    private Long id;
    private Long number;
    private String status;
    private ItemDTO item;
    private Long itemAmount;
    private BigDecimal price;
    private LocalDate dateAdded;
    private UserDTO user;
}
