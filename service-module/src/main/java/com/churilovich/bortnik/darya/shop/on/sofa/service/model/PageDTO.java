package com.churilovich.bortnik.darya.shop.on.sofa.service.model;

import java.util.ArrayList;
import java.util.List;

public class PageDTO<D> {
    private List<D> list = new ArrayList<>();
    private Long pagesAmount;
    private Long currentPageNumber;

    public List<D> getList() {
        return list;
    }

    public void setList(List<D> list) {
        this.list = list;
    }

    public Long getPagesAmount() {
        return pagesAmount;
    }

    public void setPagesAmount(Long pagesAmount) {
        this.pagesAmount = pagesAmount;
    }

    public Long getCurrentPageNumber() {
        return currentPageNumber;
    }

    public void setCurrentPageNumber(Long currentPageNumber) {
        this.currentPageNumber = currentPageNumber;
    }
}
