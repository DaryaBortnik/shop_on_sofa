package com.churilovich.bortnik.darya.shop.on.sofa.web.controller.web.sale;

import com.churilovich.bortnik.darya.shop.on.sofa.service.ArticleService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ArticleDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserDTOLogin;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user/sale")
@RequiredArgsConstructor
public class SaleStartWebController {
    private final ArticleService articleService;

    @GetMapping("/start")
    public String getLatestArticles(@AuthenticationPrincipal UserDTOLogin userDTOLogin, Model model) {
        List<ArticleDTO> articles = articleService.findLatestBySaleUserId(userDTOLogin);
        model.addAttribute("articles", articles);
        return "sale_start_page";
    }
}
