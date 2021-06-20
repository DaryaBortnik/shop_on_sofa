package com.churilovich.bortnik.darya.shop.on.sofa.service.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(exclude = {"uniqueNumber", "id"})
public class ItemDTO {
    private Long id;
    private String uniqueNumber;
    @NotBlank(message = "This field must be filled")
    private String name;
    @NotNull
    private BigDecimal price;
    @Size(max = 200, message = "Invalid size: max size = 200")
    private String description;
    private ItemCategoryDTO itemCategoryDTO;
    private Long userId;
}
