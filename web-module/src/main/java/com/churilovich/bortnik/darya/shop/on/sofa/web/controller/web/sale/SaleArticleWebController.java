package com.churilovich.bortnik.darya.shop.on.sofa.web.controller.web.sale;

import com.churilovich.bortnik.darya.shop.on.sofa.service.ArticleService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.CommentService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.AddServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.DeleteByIdServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.GetOnPageServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ArticleDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.PageDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserDTOLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/user/sale/articles")
public class SaleArticleWebController {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final ArticleService articleService;
    private final CommentService commentService;

    @Autowired
    public SaleArticleWebController(ArticleService articleService, CommentService commentService) {
        this.articleService = articleService;
        this.commentService = commentService;
    }

    @GetMapping
    public String getAllArticles(@RequestParam(defaultValue = "1", value = "current_page") Long currentPageNumber,
                                 Model model) {
        try {
            PageDTO<ArticleDTO> pageWithArticles = articleService.getArticlesOnPage(currentPageNumber);
            model.addAttribute("articles", pageWithArticles.getList());
            model.addAttribute("page", pageWithArticles);
            return "sale_all_articles_page";
        } catch (GetOnPageServiceException e) {
            logger.error(e.getMessage(), e);
            return "error_page";
        }
    }

    @GetMapping("/description")
    public String getChosenArticlePage(@RequestParam(required = false, name = "chosen_article") Long id, Model model) {
        ArticleDTO article = articleService.getWithComments(id);
        model.addAttribute("article", article);
        return "sale_article_page";
    }

    @PostMapping("/description/comments/delete")
    public String deleteArticleComments(@RequestParam("delete_comment_id") List<Long> ids) {
        try {
            ids.stream()
                    .filter(Objects::nonNull)
                    .forEach(commentService::deleteById);
            return "redirect:/user/sale/articles";
        } catch (DeleteByIdServiceException e) {
            logger.error(e.getMessage(), e);
            return "error_page";
        }
    }

    @PostMapping("/delete")
    public String deleteArticle(@RequestParam("delete_article_id") List<Long> ids) {
        try {
            ids.stream()
                    .filter(Objects::nonNull)
                    .forEach(articleService::deleteById);
            return "redirect:/user/sale/articles";
        } catch (DeleteByIdServiceException e) {
            logger.error(e.getMessage(), e);
            return "error_page";
        }
    }

    @GetMapping("/add")
    public String getAddNewArticlePage(Model model) {
        model.addAttribute("article", new ArticleDTO());
        return "sale_add_new_article_page";
    }


    @PostMapping("/add")
    public String addItem(@AuthenticationPrincipal UserDTOLogin userDTOLogin, @Valid ArticleDTO article, BindingResult result) {
        try {
            if (result.hasErrors()) {
                return "sale_add_new_article_page";
            } else {
                articleService.addWithUser(article, userDTOLogin);
                return "redirect:/user/sale/articles";
            }
        } catch (AddServiceException e) {
            logger.error(e.getMessage(), e);
            return "error_page";
        }
    }
}
