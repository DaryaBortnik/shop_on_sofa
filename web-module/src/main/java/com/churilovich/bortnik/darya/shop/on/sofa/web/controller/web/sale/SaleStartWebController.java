package com.churilovich.bortnik.darya.shop.on.sofa.web.controller.web.sale;

import com.churilovich.bortnik.darya.shop.on.sofa.service.ArticleService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ArticleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user/sale")
public class SaleStartWebController {
    private final ArticleService articleService;

    @Autowired
    public SaleStartWebController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/start")
    public String getAllItems(Model model) {
        List<ArticleDTO> all = articleService.findAll();
        List<ArticleDTO> articles = all.subList(0, all.size());
        model.addAttribute("articles", articles);
        return "sale_start_page";
    }
}
