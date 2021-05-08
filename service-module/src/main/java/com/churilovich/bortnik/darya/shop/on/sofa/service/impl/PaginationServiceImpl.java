package com.churilovich.bortnik.darya.shop.on.sofa.service.impl;

import com.churilovich.bortnik.darya.shop.on.sofa.service.PaginationService;
import org.springframework.stereotype.Service;

@Service
public class PaginationServiceImpl implements PaginationService {
    @Override
    public Long getElementPosition(Long currentElementNumber, Long elementsAmountOnOnePage) {
        if (currentElementNumber == 0) {
            currentElementNumber++;
        }
        return elementsAmountOnOnePage * (currentElementNumber - 1);
    }

    @Override
    public Long getAmountOfPagesForElements(Long amountOfElements, Long elementsAmountOnOnePage) {
        long amountOfPages;
        if (amountOfElements % elementsAmountOnOnePage > 0) {
            amountOfPages = (amountOfElements / elementsAmountOnOnePage) + 1;
        } else {
            amountOfPages = amountOfElements / elementsAmountOnOnePage;
        }
        return amountOfPages;
    }

    @Override
    public Long getCurrentPageNumber(Long currentPageNumber, Long amountOfPages) {
        if (currentPageNumber > amountOfPages) {
            currentPageNumber = amountOfPages;
        } else if (currentPageNumber < amountOfPages) {
            currentPageNumber = 1L;
        }
        return currentPageNumber;
    }
}
