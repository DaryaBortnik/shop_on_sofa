package com.churilovich.bortnik.darya.shop.on.sofa.web.controller.web.sale;

import com.churilovich.bortnik.darya.shop.on.sofa.service.ArticleService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.CommentService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.ValidationService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ArticleDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserDTOLogin;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.element.PageDTO;
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
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/user/sale/articles")
@RequiredArgsConstructor
public class SaleArticleWebController {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final ArticleService articleService;
    private final CommentService commentService;
    private final ValidationService validationService;

    @GetMapping
    public String getAllArticles(@RequestParam(defaultValue = "1", value = "current_page") Long currentPageNumber,
                                 @AuthenticationPrincipal UserDTOLogin userDTOLogin,
                                 Model model) {
        PageDTO<ArticleDTO> pageWithArticles = articleService.getBySaleUserIdOnPage(currentPageNumber, userDTOLogin);
        model.addAttribute("articles", pageWithArticles.getList());
        model.addAttribute("page", pageWithArticles);
        return "sale_all_articles_page";
    }

    @GetMapping("/description")
    public String getChosenArticlePage(@RequestParam(required = false, name = "chosen_article") Long id, Model model) {
        ArticleDTO article = articleService.getWithComments(id);
        model.addAttribute("article", article);
        return "sale_article_page";
    }

    @PostMapping("/description/comments/delete")
    public String deleteArticleComments(@RequestParam("delete_comment_id") List<Long> ids) {
        ids.stream()
                .filter(Objects::nonNull)
                .forEach(commentService::deleteById);
        return "redirect:/user/sale/articles";
    }

    @PostMapping("/delete")
    public String deleteArticle(@RequestParam("delete_article_id") List<Long> ids) {
        ids.stream()
                .filter(Objects::nonNull)
                .forEach(articleService::deleteById);
        return "redirect:/user/sale/articles";
    }

    @GetMapping("/add")
    public String getAddNewArticlePage(Model model) {
        model.addAttribute("article", new ArticleDTO());
        return "sale_add_new_article_page";
    }

    @PostMapping("/add")
    public String addArticle(@AuthenticationPrincipal UserDTOLogin userDTOLogin, @Valid ArticleDTO article, BindingResult result) {
        if (validationService.isUserHasFirstAndLastNames(userDTOLogin)) {
            if (result.hasErrors()) {
                return "sale_add_new_article_page";
            } else {
                articleService.add(article, userDTOLogin);
                return "redirect:/user/sale/articles";
            }
        } else {
            return "redirect:/user/profile";
        }
    }

    @GetMapping("/description/update")
    public String getUpdateArticleDetailsPage(@RequestParam(required = false, name = "update_article_id") Long id,
                                              Model model) {
        ArticleDTO article = articleService.findById(id);
        model.addAttribute("article", article);
        return "sale_update_article_details_page";
    }

    @PostMapping("/description/update")
    public String updateParameters(@RequestParam(required = false, name = "article_id") Long id,
                                   ArticleDTO article,
                                   BindingResult result) {
        if (result.hasErrors()) {
            return "sale_update_article_details_page";
        } else {
            article.setId(id);
            articleService.updateArticle(article);
            return "redirect:/user/sale/articles";
        }
    }
}
