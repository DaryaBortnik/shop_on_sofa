package com.churilovich.bortnik.darya.shop.on.sofa.service;

import com.churilovich.bortnik.darya.shop.on.sofa.service.model.PageDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ReviewDTO;

public interface ReviewService {
    void delete(Long id);

    void updateShownStatus(Long id);

    PageDTO<ReviewDTO> getReviewsOnPage(Long currentPageNumber);
}
