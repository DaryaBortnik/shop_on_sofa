package com.churilovich.bortnik.darya.shop.on.sofa.web.controller.web.customer;

import com.churilovich.bortnik.darya.shop.on.sofa.service.ReviewService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.ValidationService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ReviewDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserDTOLogin;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.lang.invoke.MethodHandles;

@Controller
@RequestMapping("/user/customer")
@RequiredArgsConstructor
public class CustomerReviewWebController {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final ReviewService reviewService;
    private final ValidationService validationService;

    @GetMapping("reviews/add")
    public String getAddNewReviewPage(Model model) {
        model.addAttribute("review", new ReviewDTO());
        return "customer_add_new_review_page";
    }

    @PostMapping("reviews/add")
    public String addReview(@AuthenticationPrincipal UserDTOLogin userDTOLogin, @Valid ReviewDTO review, BindingResult result) {
        if (validationService.isUserHasFirstAndLastNames(userDTOLogin)) {
            if (result.hasErrors()) {
                return "customer_add_new_review_page";
            } else {
                reviewService.add(review, userDTOLogin);
                return "redirect:/user/customer/start";
            }
        } else {
            return "redirect:/user/profile";
        }
    }
}
