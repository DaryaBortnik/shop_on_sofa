package com.churilovich.bortnik.darya.shop.on.sofa.service.impl;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.ReviewRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.exception.GenericRepositoryRuntimeException;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.Review;
import com.churilovich.bortnik.darya.shop.on.sofa.service.PaginationService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.ReviewService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.ReviewServiceRuntimeException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.PageDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ReviewDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.churilovich.bortnik.darya.shop.on.sofa.service.constants.PaginationValueConstants.AMOUNT_ON_ONE_PAGE;

@Service
public class ReviewServiceImpl implements ReviewService {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final ReviewRepository reviewRepository;
    private final ConversionService conversionService;
    private final PaginationService paginationService;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository,
                             ConversionService conversionService,
                             PaginationService paginationService) {
        this.reviewRepository = reviewRepository;
        this.conversionService = conversionService;
        this.paginationService = paginationService;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        logger.info("Deleting review by id [{}]", id);
        Review review = reviewRepository.findById(id);
        reviewRepository.remove(review);
    }

    @Override
    @Transactional
    public void updateShownStatus(Long id) {
        logger.info("Updating shown status for review id [{}] on service level", id);
        Review review = reviewRepository.findById(id);
        if (Objects.nonNull(review)) {
            boolean shown = review.isShown();
            review.setShown(!shown);
            reviewRepository.merge(review);
        } else {
            throw new ReviewServiceRuntimeException("Can't update review shown status : review id = " + id);
        }
    }

    @Override
    @Transactional
    public PageDTO<ReviewDTO> getReviewsOnPage(Long currentPageNumber) {
        logger.info("Getting all reviews on current page [{}] on service level", currentPageNumber);
        try {
            Long amountOfReviews = reviewRepository.getAmountOfEntities();
            Long amountOfPages = paginationService.getAmountOfPagesForElements(amountOfReviews, AMOUNT_ON_ONE_PAGE);
            return buildPageWithReviews(currentPageNumber, amountOfPages);
        } catch (GenericRepositoryRuntimeException e) {
            logger.error(e.getMessage(), e);
            throw new ReviewServiceRuntimeException("Can't get all reviews on current page on service level " +
                    "due to impossibility to get total amount of reviews", e);
        }
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

