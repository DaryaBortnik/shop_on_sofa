package com.churilovich.bortnik.darya.shop.on.sofa.web.controller.web.sale;

import com.churilovich.bortnik.darya.shop.on.sofa.service.ShopService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ShopDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserDTOLogin;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/sale/shop")
@RequiredArgsConstructor
public class SaleShopWebController {
    private final ShopService shopService;

    @GetMapping
    public String getShopProfilePage(@AuthenticationPrincipal UserDTOLogin userDTOLogin, Model model) {
        ShopDTO shop = shopService.findBySaleUserId(userDTOLogin.getUserId());
        model.addAttribute("shop", shop);
        return "sale_get_shop_profile_page";
    }

    @GetMapping("/update/parameters")
    public String getUpdateShopProfilePage(@AuthenticationPrincipal UserDTOLogin userDTOLogin, Model model) {
        ShopDTO shop = shopService.findBySaleUserId(userDTOLogin.getUserId());
        model.addAttribute("shop", shop);
        return "sale_update_shop_profile_page";
    }

    @PostMapping("/update/parameters")
    public String updateParameters(@AuthenticationPrincipal UserDTOLogin userDTOLogin, ShopDTO shop,
                                   BindingResult result) {
        if (result.hasErrors()) {
            return "sale_update_shop_profile_page";
        } else {
            shopService.updateShopProfileParameters(userDTOLogin.getUserId(), shop);
            return "redirect:/user/sale/shop";
        }
    }

    @GetMapping("/add")
    public String getAddShopPage(Model model) {
        model.addAttribute("shop", new ShopDTO());
        return "sale_add_new_shop_page";
    }

    @PostMapping("/add")
    public String updateParameters(@AuthenticationPrincipal UserDTOLogin userDTOLogin, ShopDTO shop) {
        shop.setUserDTO(userDTOLogin.getUser());
        shopService.add(shop);
        return "redirect:/user/sale/shop";
    }
}

