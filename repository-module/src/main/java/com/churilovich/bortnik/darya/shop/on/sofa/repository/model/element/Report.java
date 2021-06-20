package com.churilovich.bortnik.darya.shop.on.sofa.repository.model.element;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Report {
    private String name;
    private String category;
    private BigDecimal price;
    private Long amount;
}
