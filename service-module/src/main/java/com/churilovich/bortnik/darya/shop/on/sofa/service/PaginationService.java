package com.churilovich.bortnik.darya.shop.on.sofa.service;

public interface PaginationService<E> {
    Long getStartEntityNumberOnCurrentPage(Long currentPageNumber, Long amountOfPages, Long EntityAmountOnPage);

    Long getAmountOfPages(E repository);

    Long getAmountOfPagesByUserId(E repository, Long userId);

    Long getAmountOfPagesForUsersReviews(E repository);
}
