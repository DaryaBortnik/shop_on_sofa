package com.churilovich.bortnik.darya.shop.on.sofa.service.impl;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.ReviewRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.exception.GetEntitiesAmountRepositoryException;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.Review;
import com.churilovich.bortnik.darya.shop.on.sofa.service.PaginationService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.ReviewService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.UserService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.DeleteByIdServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.GetOnPageServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.UpdateParameterServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.OrderDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ReviewDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserDTOLogin;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.element.PageDTO;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.churilovich.bortnik.darya.shop.on.sofa.service.constants.PaginationValueConstants.AMOUNT_ON_ONE_PAGE;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final ConversionService conversionService;
    private final PaginationService<ReviewRepository> paginationService;

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
            Long amountOfPages = paginationService.getAmountOfPages(reviewRepository);
            return buildPageWithReviews(currentPageNumber, amountOfPages);
        } catch (GetEntitiesAmountRepositoryException e) {
            logger.error(e.getMessage(), e);
            throw new GetOnPageServiceException("Can't get all reviews on current page on service level " +
                    "due to impossibility to get total amount of reviews", e);
        }
    }

    @Override
    @Transactional
    public ReviewDTO add(ReviewDTO reviewDTO, UserDTOLogin userDTOLogin) {
        UserDTO user = userService.findById(userDTOLogin.getUserId());
        reviewDTO.setDate(LocalDate.now());
        reviewDTO.setUserProfileDTO(user.getUserProfileDTO());
        reviewDTO.setIsShown(false);
        Review review = conversionService.convert(reviewDTO, Review.class);
        reviewRepository.persist(review);
        return conversionService.convert(review, ReviewDTO.class);
    }

    private void updateStatus(Review review) {
        boolean shown = review.getIsShown();
        review.setIsShown(!shown);
        reviewRepository.merge(review);
    }

    private PageDTO<ReviewDTO> buildPageWithReviews(Long currentPageNumber, Long amountOfPages) {
        PageDTO<ReviewDTO> page = getPageWithReviews(amountOfPages);
        Long startNumberOnCurrentPage = paginationService.getStartEntityNumberOnCurrentPage(currentPageNumber, amountOfPages, AMOUNT_ON_ONE_PAGE);
        List<Review> reviews = reviewRepository.findAll(startNumberOnCurrentPage, AMOUNT_ON_ONE_PAGE);
        addReviewsToPage(page, reviews);
        return page;
    }

    private PageDTO<ReviewDTO> getPageWithReviews(Long amountOfPages) {
        PageDTO<ReviewDTO> page = new PageDTO<>();
        page.setPagesAmount(amountOfPages);
        return page;
    }

    private void addReviewsToPage(PageDTO<ReviewDTO> page, List<Review> reviews) {
        List<ReviewDTO> reviewsDTO = getReviews(reviews);
        page.getList().addAll(reviewsDTO);
    }

    private List<ReviewDTO> getReviews(List<Review> reviews) {
        return reviews.stream()
                .map(review -> conversionService.convert(review, ReviewDTO.class))
                .collect(Collectors.toList());
    }
}

