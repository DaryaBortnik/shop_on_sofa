package com.churilovich.bortnik.darya.shop.on.sofa.service.model.element;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Data
public class ReportDTO {
    private final String name;
    private final String category;
    private final BigDecimal price;
    private final Long amount;
}
