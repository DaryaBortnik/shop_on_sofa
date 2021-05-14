package com.churilovich.bortnik.darya.shop.on.sofa.service.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemDTO {
    private Long id;
    private String uniqueNumber;
    private String name;
    private BigDecimal price;
    private String description;
    private String category;
    private Long userId;
}
