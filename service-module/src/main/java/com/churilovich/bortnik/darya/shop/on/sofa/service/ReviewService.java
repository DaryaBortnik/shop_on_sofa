package com.churilovich.bortnik.darya.shop.on.sofa.service;

import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ReviewDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserDTOLogin;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.element.PageDTO;

public interface ReviewService {
    void deleteById(Long id);

    void updateShownStatus(Long id);

    PageDTO<ReviewDTO> getReviewsOnPage(Long currentPageNumber);

    ReviewDTO add(ReviewDTO review, UserDTOLogin userDTOLogin);

    PageDTO<ReviewDTO> getAllOnPageForUsers(Long currentPageNumber);
}
