package com.churilovich.bortnik.darya.shop.on.sofa.service.model.element;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageDTO<D> {
    private List<D> list = new ArrayList<>();
    private Long pagesAmount;
    private Long currentPageNumber;
}
