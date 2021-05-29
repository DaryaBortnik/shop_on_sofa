package com.churilovich.bortnik.darya.shop.on.sofa.web.controller.web.customer;

import com.churilovich.bortnik.darya.shop.on.sofa.service.ItemService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.GetOnPageServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ItemDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.element.PageDTO;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Controller
@RequestMapping("/user/customer")
@RequiredArgsConstructor
public class CustomerItemWebController {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final ItemService itemService;

    @GetMapping("/items")
    public String getAllItems(@RequestParam(defaultValue = "1", value = "current_page") Long currentPageNumber,
                              Model model) {
        try {
            PageDTO<ItemDTO> pageWithItems = itemService.getAllOnPage(currentPageNumber);
            model.addAttribute("items", pageWithItems.getList());
            model.addAttribute("page", pageWithItems);
            return "customer_get_all_items_page";
        } catch (GetOnPageServiceException e) {
            logger.error(e.getMessage(), e);
            return "error_page";
        }
    }

    @GetMapping("/items/description")
    public String getChosenItemPage(@RequestParam(required = false, name = "chosen_item") Long id, Model model) {
        ItemDTO item = itemService.findById(id);
        model.addAttribute("item", item);
        return "customer_get_item_page";
    }

    @GetMapping("/items/category")
    public String getItemsByCategoryPage(@RequestParam(required = false, name = "chosen_category") Long id,
                                         Model model) {
        List<ItemDTO> items = itemService.findByCategoryId(id);
        model.addAttribute("items", items);
        return "customer_get_items_with_category_page";
    }
}
