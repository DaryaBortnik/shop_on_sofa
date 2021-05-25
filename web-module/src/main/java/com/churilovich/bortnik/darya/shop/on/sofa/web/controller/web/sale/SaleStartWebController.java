package com.churilovich.bortnik.darya.shop.on.sofa.web.controller.web.sale;

import com.churilovich.bortnik.darya.shop.on.sofa.service.ArticleService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ArticleDTO;
import lombok.RequiredArgsConstructor;
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
    public String getAllItems(Model model) {
        List<ArticleDTO> articles = articleService.findAll();
        model.addAttribute("articles", articles);
        return "sale_start_page";
    }
}
