package com.churilovich.bortnik.darya.shop.on.sofa.web.controller.web.customer;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.Item;
import com.churilovich.bortnik.darya.shop.on.sofa.service.ArticleService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.ItemService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.ShopService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ArticleDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ItemDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ShopDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/user/customer/sales")
@RequiredArgsConstructor
public class CustomerShopWebController {
    private final ShopService shopService;
    private final ItemService itemService;
    private final ArticleService articleService;

    @GetMapping("/description")
    public String getShopInformation(@RequestParam(required = false, value = "chosen_sale") Long id, Model model) {
        ShopDTO shop = shopService.findById(id);
        model.addAttribute("shop", shop);
        List<ItemDTO> items = itemService.findInShop(shop);
        model.addAttribute("items", items);
        List<ArticleDTO> articles = articleService.findByShop(shop);
        model.addAttribute("articles", articles);
        return "custom_shop_page";
    }
}
