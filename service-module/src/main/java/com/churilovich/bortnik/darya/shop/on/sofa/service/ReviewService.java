package com.churilovich.bortnik.darya.shop.on.sofa.service;

import com.churilovich.bortnik.darya.shop.on.sofa.service.model.element.PageDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ReviewDTO;

public interface ReviewService {
    void deleteById(Long id);

    void updateShownStatus(Long id);

    PageDTO<ReviewDTO> getReviewsOnPage(Long currentPageNumber);
}
