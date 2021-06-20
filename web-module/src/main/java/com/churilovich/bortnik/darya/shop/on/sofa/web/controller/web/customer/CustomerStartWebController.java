package com.churilovich.bortnik.darya.shop.on.sofa.web.controller.web.customer;

import com.churilovich.bortnik.darya.shop.on.sofa.service.ArticleService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.ItemCategoryService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.ShopService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ArticleDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ItemCategoryDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ShopDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user/customer")
@RequiredArgsConstructor
public class CustomerStartWebController {
    private final ItemCategoryService categoryService;
    private final ShopService shopService;
    private final ArticleService articleService;

    @GetMapping("/start")
    public String getAllCategories(Model model) {
        List<ItemCategoryDTO> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        List<ShopDTO> sales = shopService.findAllShops();
        model.addAttribute("sales", sales);
        List<ArticleDTO> articles = articleService.findLatestFromEachSaleUser();
        model.addAttribute("articles", articles);
        return "customer_start_page";
    }
}
