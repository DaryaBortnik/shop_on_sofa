package com.churilovich.bortnik.darya.shop.on.sofa.web.controller.web.common;

import com.churilovich.bortnik.darya.shop.on.sofa.service.ReviewService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ReviewDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.element.PageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user/reviews")
@RequiredArgsConstructor
public class ReviewsWebController {
    private final ReviewService reviewService;

    @GetMapping
    public String getAllArticles(@RequestParam(defaultValue = "1", value = "current_page") Long currentPageNumber,
                                 Model model) {
        PageDTO<ReviewDTO> pageWithReviews = reviewService.getAllOnPageForUsers(currentPageNumber);
        model.addAttribute("reviews", pageWithReviews.getList());
        model.addAttribute("page", pageWithReviews);
        return "common_get_all_reviews_page";
    }
}
