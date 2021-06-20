package com.churilovich.bortnik.darya.shop.on.sofa.web.controller.web.sale;

import com.churilovich.bortnik.darya.shop.on.sofa.service.ItemCategoryService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.ItemService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.ReportService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ItemCategoryDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ItemDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserDTOLogin;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.element.PageDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.element.ReportDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/user/sale")
@RequiredArgsConstructor
public class SaleItemWebController {
    private final ItemService itemService;
    private final ItemCategoryService categoryService;
    private final ReportService reportService;

    @GetMapping("/items")
    public String getAllItems(@RequestParam(defaultValue = "1", value = "current_page") Long currentPageNumber,
                              @AuthenticationPrincipal UserDTOLogin userDTOLogin, Model model) {
        PageDTO<ItemDTO> pageWithItems = itemService.getBySaleUserIdOnPage(currentPageNumber, userDTOLogin);
        model.addAttribute("items", pageWithItems.getList());
        model.addAttribute("page", pageWithItems);
        return "sale_get_all_items_page";
    }

    @GetMapping("/items/description")
    public String getChosenItemPage(@RequestParam(required = false, name = "chosen_item") Long id, Model model) {
        ItemDTO item = itemService.findById(id);
        model.addAttribute("item", item);
        return "sale_get_item_page";
    }

    @GetMapping("/items/description/update")
    public String getUpdateItemDetailsPage(@RequestParam(required = false, name = "update_item_id") Long id,
                                           Model model) {
        ItemDTO item = itemService.findById(id);
        model.addAttribute("item", item);
        List<ItemCategoryDTO> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "sale_update_item_details_page";
    }

    @PostMapping("/items/description/update")
    public String updateParameters(@RequestParam(required = false, name = "item_id") Long id,
                                   ItemDTO item,
                                   BindingResult result) {
        if (result.hasErrors()) {
            return "sale_update_item_details_page";
        } else {
            item.setId(id);
            itemService.updateItemDetails(item);
            return "redirect:/user/sale/items";
        }
    }

    @PostMapping("/items/delete")
    public String deleteItem(@RequestParam("deleting_item") List<Long> ids) {
        ids.stream()
                .filter(Objects::nonNull)
                .forEach(itemService::deleteById);
        return "redirect:/user/sale/items";
    }

    @PostMapping("/items/copy")
    public String copyItem(@RequestParam(required = false, name = "copy_item_id") Long id) {
        itemService.copy(id);
        return "redirect:/user/sale/items";
    }

    @GetMapping("/items/add")
    public String getAddNewItemPage(ItemDTO item, Model model) {
        List<ItemCategoryDTO> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        item.setItemCategoryDTO(new ItemCategoryDTO());
        model.addAttribute("item", new ItemDTO());
        return "sale_add_new_item_page";
    }

    @PostMapping("/items/add")
    public String addItem(@AuthenticationPrincipal UserDTOLogin userDTOLogin, @Valid ItemDTO item, BindingResult result) {
        if (result.hasErrors()) {
            return "sale_add_new_item_page";
        } else {
            item.setUserId(userDTOLogin.getUserId());
            itemService.add(item);
            return "redirect:/user/sale/items";
        }
    }

    @GetMapping("/items/report")
    public String getReport(@AuthenticationPrincipal UserDTOLogin userDTOLogin, Model model) {
        List<ReportDTO> reports = reportService.get(userDTOLogin.getUserId());
        model.addAttribute("reports", reports);
        return "sale_get_item_report_page";
    }
}