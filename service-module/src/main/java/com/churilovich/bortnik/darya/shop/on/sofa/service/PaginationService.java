package com.churilovich.bortnik.darya.shop.on.sofa.service;

public interface PaginationService {
    Long getElementPosition(Long currentElementNumber, Long elementsAmountOnOnePage);

    Long getAmountOfPagesForElements(Long amountOfElements, Long elementsAmountOnOnePage);

    Long getCurrentPageNumber(Long currentPageNumber, Long amountOfPages);
}
