package com.churilovich.bortnik.darya.shop.on.sofa.web.controller.web.sale;

import com.churilovich.bortnik.darya.shop.on.sofa.service.CategoryService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.ItemService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.ReportService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.AddServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.DeleteByIdServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.GetItemsServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ItemCategoryDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ItemDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.PageDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ReportDTO;
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
@RequestMapping("/user/sale")
public class SaleItemWebController {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final ItemService itemService;
    private final CategoryService categoryService;
    private final ReportService reportService;

    @Autowired
    public SaleItemWebController(ItemService itemService,
                                 CategoryService categoryService,
                                 ReportService reportService) {
        this.itemService = itemService;
        this.categoryService = categoryService;
        this.reportService = reportService;
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

    @GetMapping("/items/description/update")
    public String getUpdateItemDetailsPage(@RequestParam(required = false, name = "update_item_id") Long id,
                                           Model model) {
        ItemDTO item = itemService.findById(id);
        model.addAttribute("item", item);
        List<ItemCategoryDTO> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "update_item_details_page";
    }

    @PostMapping("/items/description/update")
    public String updateParameters(@RequestParam(required = false, name = "item_id") Long id,
                                   ItemDTO item,
                                   BindingResult result) {
        if (result.hasErrors()) {
            return "update_item_details_page";
        } else {
            item.setId(id);
            itemService.updateItemDetails(item);
            return "redirect:/user/sale/items";
        }
    }

    @PostMapping("/items/delete")
    public String deleteItem(@RequestParam("deleting_item") List<Long> ids) {
        try {
            ids.stream()
                    .filter(Objects::nonNull)
                    .forEach(itemService::deleteById);
            return "redirect:/user/sale/items";
        } catch (DeleteByIdServiceException e) {
            logger.error(e.getMessage(), e);
            return "error_page";
        }
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
        return "add_new_item_page";
    }


    @PostMapping("/items/add")
    public String addItem(@AuthenticationPrincipal UserDTOLogin userDTOLogin, @Valid ItemDTO item, BindingResult result) {
        try {
            if (result.hasErrors()) {
                return "add_new_item_page";
            } else {
                item.setUserId(userDTOLogin.getUserId());
                itemService.add(item);
                return "redirect:/user/sale/items";
            }
        } catch (AddServiceException e) {
            logger.error(e.getMessage(), e);
            return "error_page";
        }
    }

    @GetMapping("/items/report")
    public String getReport(Model model) {
        List<ReportDTO> reports = reportService.get();
        model.addAttribute("reports", reports);
        return "get_item_report_page";
    }
}