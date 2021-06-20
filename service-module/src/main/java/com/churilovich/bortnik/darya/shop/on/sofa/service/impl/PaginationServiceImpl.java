package com.churilovich.bortnik.darya.shop.on.sofa.service.impl;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.impl.GenericRepositoryImpl;
import com.churilovich.bortnik.darya.shop.on.sofa.service.PaginationService;
import org.springframework.stereotype.Service;

import static com.churilovich.bortnik.darya.shop.on.sofa.service.constants.PaginationValueConstants.AMOUNT_ON_ONE_PAGE;

@Service
public class PaginationServiceImpl<E> implements PaginationService<GenericRepositoryImpl<Long, E>> {

    @Override
    public Long getStartEntityNumberOnCurrentPage(Long currentPageNumber, Long amountOfPages, Long EntityAmountOnPage) {
        Long currentPage = getCurrentPageNumber(currentPageNumber, amountOfPages);
        return getElementPosition(currentPage, AMOUNT_ON_ONE_PAGE);
    }

    @Override
    public Long getAmountOfPages(GenericRepositoryImpl<Long, E> repository) {
        Long amountOfEntities = repository.getAmountOfEntities();
        return getAmountOfPagesForElements(amountOfEntities, AMOUNT_ON_ONE_PAGE);
    }

    @Override
    public Long getAmountOfPagesByUserId(GenericRepositoryImpl<Long, E> repository,Long userId) {
        Long amountOfEntities = repository.getAmountOfEntitiesSelectedByUserId(userId);
        return getAmountOfPagesForElements(amountOfEntities, AMOUNT_ON_ONE_PAGE);
    }

    @Override
    public Long getAmountOfPagesForUsersReviews(GenericRepositoryImpl<Long, E> repository) {
        Long amountOfEntities = repository.getAmountOfEntityForUsers();
        return getAmountOfPagesForElements(amountOfEntities, AMOUNT_ON_ONE_PAGE);
    }

    private Long getElementPosition(Long currentElementNumber, Long elementsAmountOnOnePage) {
        if (currentElementNumber == 0) {
            currentElementNumber++;
        }
        return elementsAmountOnOnePage * (currentElementNumber - 1);
    }

    private Long getCurrentPageNumber(Long currentPageNumber, Long amountOfPages) {
        if (currentPageNumber > amountOfPages) {
            currentPageNumber = amountOfPages;
        } else if (currentPageNumber < amountOfPages) {
            currentPageNumber = 1L;
        }
        return currentPageNumber;
    }

    private Long getAmountOfPagesForElements(Long amountOfElements, Long elementsAmountOnOnePage) {
        long amountOfPages;
        if (amountOfElements % elementsAmountOnOnePage > 0) {
            amountOfPages = (amountOfElements / elementsAmountOnOnePage) + 1;
        } else {
            amountOfPages = amountOfElements / elementsAmountOnOnePage;
        }
        return amountOfPages;
    }
}
