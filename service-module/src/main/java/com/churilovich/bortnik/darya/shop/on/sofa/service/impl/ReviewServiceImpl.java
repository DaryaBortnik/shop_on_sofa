package com.churilovich.bortnik.darya.shop.on.sofa.service.impl;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.ReviewRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.exception.GetEntitiesAmountRepositoryException;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.Review;
import com.churilovich.bortnik.darya.shop.on.sofa.service.PaginationService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.ReviewService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.DeleteByIdServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.GetOnPageServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.UpdateParameterServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.element.PageDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ReviewDTO;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.stream.Collectors;

import static com.churilovich.bortnik.darya.shop.on.sofa.service.constants.PaginationValueConstants.AMOUNT_ON_ONE_PAGE;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final ReviewRepository reviewRepository;
    private final ConversionService conversionService;
    private final PaginationService paginationService;

    @Override
    @Transactional
    public void deleteById(Long id) {
        reviewRepository.findById(id)
                .ifPresentOrElse(reviewRepository::remove, () -> {
                    throw new DeleteByIdServiceException("Review can't deleteById on service level because review" +
                            " with this id id not found : id = " + id);
                });
    }

    @Override
    @Transactional
    public void updateShownStatus(Long id) {
        reviewRepository.findById(id)
                .ifPresentOrElse(this::updateStatus, () -> {
                    throw new UpdateParameterServiceException("Can't update review shown status : review id = " + id);
                });
    }

    @Override
    @Transactional
    public PageDTO<ReviewDTO> getReviewsOnPage(Long currentPageNumber) {
        try {
            Long amountOfReviews = reviewRepository.getAmountOfEntities();
            Long amountOfPages = paginationService.getAmountOfPagesForElements(amountOfReviews, AMOUNT_ON_ONE_PAGE);
            return buildPageWithReviews(currentPageNumber, amountOfPages);
        } catch (GetEntitiesAmountRepositoryException e) {
            logger.error(e.getMessage(), e);
            throw new GetOnPageServiceException("Can't get all reviews on current page on service level " +
                    "due to impossibility to get total amount of reviews", e);
        }
    }

    private void updateStatus(Review review) {
        boolean shown = review.getIsShown();
        review.setIsShown(!shown);
        reviewRepository.merge(review);
    }

    private PageDTO<ReviewDTO> buildPageWithReviews(Long currentPageNumber, Long amountOfPages) {
        PageDTO<ReviewDTO> page = new PageDTO<>();
        page.setPagesAmount(amountOfPages);
        currentPageNumber = paginationService.getCurrentPageNumber(currentPageNumber, amountOfPages);
        Long startNumberOnCurrentPage = paginationService.getElementPosition(currentPageNumber, AMOUNT_ON_ONE_PAGE);
        List<Review> reviews = reviewRepository.findAll(startNumberOnCurrentPage, AMOUNT_ON_ONE_PAGE);
        List<ReviewDTO> reviewsDTO = getReviewsDTO(reviews);
        page.setList(reviewsDTO);
        return page;
    }

    private List<ReviewDTO> getReviewsDTO(List<Review> reviews) {
        return reviews.stream()
                .map(review -> conversionService.convert(review, ReviewDTO.class))
                .collect(Collectors.toList());
    }
}

