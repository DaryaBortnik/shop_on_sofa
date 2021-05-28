package com.churilovich.bortnik.darya.shop.on.sofa.web.controller.web.admin;

import com.churilovich.bortnik.darya.shop.on.sofa.service.ReviewService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.DeleteByIdServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.GetOnPageServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.UpdateParameterServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.element.PageDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ReviewDTO;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
@RequiredArgsConstructor
public class AdminReviewWebController {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final ReviewService reviewService;

    @GetMapping("/reviews")
    public String getAllReviews(@RequestParam(defaultValue = "1", value = "current_page") Long currentPageNumber,
                                Model model) {
        try {
            PageDTO<ReviewDTO> pageWithReviews = reviewService.getReviewsOnPage(currentPageNumber);
            model.addAttribute("reviews", pageWithReviews.getList());
            model.addAttribute("page", pageWithReviews);
            return "admin_get_all_reviews_page";
        } catch (GetOnPageServiceException e) {
            logger.error(e.getMessage(), e);
            return "error_page";
        }
    }

    @PostMapping("/reviews/delete")
    public String deleteReview(@RequestParam("deleting_review_id") Long id) {
        try {
            reviewService.deleteById(id);
            return "redirect:/admin/reviews";
        } catch (DeleteByIdServiceException e) {
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
        } catch (UpdateParameterServiceException e) {
            logger.error(e.getMessage(), e);
            return "error_page";
        }
    }
}
