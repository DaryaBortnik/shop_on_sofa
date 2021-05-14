package com.churilovich.bortnik.darya.shop.on.sofa.web.controller.web.sale;

import com.churilovich.bortnik.darya.shop.on.sofa.service.ItemService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.GetItemsServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.ReviewNotFoundServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ItemDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.PageDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/user/sale")
public class SaleItemWebController {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final ItemService itemService;

    @Autowired
    public SaleItemWebController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/items")
    public String getAllItems(@RequestParam(defaultValue = "1", value = "current_page") Long currentPageNumber,
                              Model model) {
        try {
            PageDTO<ItemDTO> pageWithItems = itemService.getItemsOnPage(currentPageNumber);
            model.addAttribute("items", pageWithItems.getList());
            model.addAttribute("page", pageWithItems);
            return "get_all_items_page";
        } catch (GetItemsServiceException e) {
            logger.error(e.getMessage(), e);
            return "error_page";
        }
    }

    @GetMapping("/items/description")
    public String getChosenItemPage(@RequestParam(required = false, name = "chosen_item") Long id, Model model) {
        ItemDTO item = itemService.findById(id);
        model.addAttribute("item", item);
        return "get_item_page";
    }

    @PostMapping("/items/delete")
    public String deleteItem(@RequestParam("deleting_item") List<Long> ids) {
        try {
            ids.stream()
                    .filter(Objects::nonNull)
                    .forEach(itemService::deleteById);
            return "redirect:/user/sale/items";
        } catch (
                ReviewNotFoundServiceException e) {
            logger.error(e.getMessage(), e);
            return "error_page";
        }
    }
}