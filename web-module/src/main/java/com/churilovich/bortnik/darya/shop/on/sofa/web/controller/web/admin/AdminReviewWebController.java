package com.churilovich.bortnik.darya.shop.on.sofa.web.controller.web.admin;

import com.churilovich.bortnik.darya.shop.on.sofa.service.ReviewService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.GetReviewsServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.ReviewNotFoundServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.UpdateReviewStatusServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.PageDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ReviewDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/admin")
public class AdminReviewWebController {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final ReviewService reviewService;

    @Autowired
    public AdminReviewWebController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/reviews")
    public String getAllReviews(@RequestParam(defaultValue = "1", value = "current_page") Long currentPageNumber,
                                Model model) {
        try {
            PageDTO<ReviewDTO> pageWithReviews = reviewService.getReviewsOnPage(currentPageNumber);
            model.addAttribute("reviews", pageWithReviews.getList());
            model.addAttribute("page", pageWithReviews);
            return "get_all_reviews_page";
        } catch (GetReviewsServiceException e) {
            logger.error(e.getMessage(), e);
            return "error_page";
        }
    }

    @PostMapping("/reviews/delete")
    public String deleteReview(@RequestParam("deleting_review_id") Long id) {
        try {
            reviewService.deleteById(id);
            return "redirect:/admin/reviews";
        } catch (ReviewNotFoundServiceException e) {
            logger.error(e.getMessage(), e);
            return "error_page";
        }
    }

    @PostMapping("/reviews/update/shown")
    public String updateShownStatus(@RequestParam("change_shown_status_review_id") List<Long> ids) {
        try {
            ids.stream()
                    .filter(Objects::nonNull)
                    .forEach(reviewService::updateShownStatus);
            return "redirect:/admin/reviews";
        } catch (UpdateReviewStatusServiceException e) {
            logger.error(e.getMessage(), e);
            return "error_page";
        }
    }
}
