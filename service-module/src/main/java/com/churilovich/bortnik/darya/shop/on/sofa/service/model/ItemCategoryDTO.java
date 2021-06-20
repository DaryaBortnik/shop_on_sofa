package com.churilovich.bortnik.darya.shop.on.sofa.service.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = "id")
public class ItemCategoryDTO {
    private Long id;
    private String name;
}
