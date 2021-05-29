package com.churilovich.bortnik.darya.shop.on.sofa.web.controller.web.customer;

import com.churilovich.bortnik.darya.shop.on.sofa.service.CommentService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.ValidationService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.AddServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.CommentDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserDTOLogin;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.lang.invoke.MethodHandles;

@Controller
@RequestMapping("/user/customer")
@RequiredArgsConstructor
public class CustomerCommentWebController {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final CommentService commentService;
    private final ValidationService validationService;

    @PostMapping("/comments/add")
    public String addComment(@AuthenticationPrincipal UserDTOLogin userDTOLogin, @Valid CommentDTO comment, BindingResult result,
                          @RequestParam(required = false, name = "chosen_article_id") Long id) {
        try {
            if (validationService.isUserHasFirstAndLastNames(userDTOLogin)) {
                if (result.hasErrors()) {
                    return "customer_get_article_page";
                } else {
                    comment.setArticleId(id);
                    commentService.add(comment, userDTOLogin);
                    return String.format("redirect:/user/customer/articles/description?chosen_article=%d", id);
                }
            } else {
                return "redirect:/user/profile";
            }
        } catch (AddServiceException e) {
            logger.error(e.getMessage(), e);
            return "error_page";
        }
    }

}
