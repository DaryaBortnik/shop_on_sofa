package com.churilovich.bortnik.darya.shop.on.sofa.web.controller.web.customer;

import com.churilovich.bortnik.darya.shop.on.sofa.service.ArticleService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.GetOnPageServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ArticleDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.PageDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.invoke.MethodHandles;

@Controller
@RequestMapping("/user/customer")
public class CustomerArticleWebController {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final ArticleService articleService;

    public CustomerArticleWebController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/articles")
    public String getAllArticles(@RequestParam(defaultValue = "1", value = "current_page") Long currentPageNumber,
                                 Model model) {
        try {
            PageDTO<ArticleDTO> pageWithArticles = articleService.getArticlesOnPage(currentPageNumber);
            model.addAttribute("articles", pageWithArticles.getList());
            model.addAttribute("page", pageWithArticles);
            return "get_all_articles_page";
        } catch (GetOnPageServiceException e) {
            logger.error(e.getMessage(), e);
            return "error_page";
        }
    }

    @GetMapping("/articles/description")
    public String getChosenArticlePage(@RequestParam(required = false, name = "chosen_article") Long id, Model model) {
        ArticleDTO article = articleService.getWithComments(id);
        model.addAttribute("article", article);
        return "get_article_page";
    }
}
