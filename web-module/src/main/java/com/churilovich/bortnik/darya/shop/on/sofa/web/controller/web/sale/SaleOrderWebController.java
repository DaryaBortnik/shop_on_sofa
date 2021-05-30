package com.churilovich.bortnik.darya.shop.on.sofa.web.controller.web.sale;

import com.churilovich.bortnik.darya.shop.on.sofa.service.OrderService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.OrderDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserDTOLogin;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.element.PageDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.enums.StatusEnum;
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

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/user/sale")
@RequiredArgsConstructor
public class SaleOrderWebController {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final OrderService orderService;

    @GetMapping("/orders")
    public String getAllOrders(@RequestParam(defaultValue = "1", value = "current_page") Long currentPageNumber,
                               @AuthenticationPrincipal UserDTOLogin userDTOLogin,
                               Model model) {
        PageDTO<OrderDTO> pageWithItems = orderService.getBySaleUserIdOnPage(currentPageNumber, userDTOLogin);
        model.addAttribute("orders", pageWithItems.getList());
        model.addAttribute("page", pageWithItems);
        return "sale_get_all_orders_page";
    }

    @GetMapping("/orders/description")
    public String getChosenOrderPage(@RequestParam(required = false, name = "chosen_order") Long id, Model model) {
        OrderDTO order = orderService.findById(id);
        model.addAttribute("order", order);
        return "sale_get_order_page";
    }

    @GetMapping("/orders/description/update")
    public String getUpdateOrderDetailsPage(@RequestParam(required = false, name = "update_order_id") Long id,
                                            Model model) {
        OrderDTO order = orderService.findById(id);
        model.addAttribute("order", order);
//        List<String> statuses = new ArrayList<>();
//        statuses.add("NEW");
//        statuses.add("IN_PROGRESS");
//        statuses.add("DELIVERED");
//        statuses.add("REJECTED");

        model.addAttribute("statuses", StatusEnum.values());
        return "sale_update_order_details_page";
    }

    @PostMapping("/orders/description/update")
    public String updateParameters(@RequestParam(required = false, name = "update_order_id") Long id,
                                   OrderDTO order,
                                   BindingResult result) {
        if (result.hasErrors()) {
            return "sale_update_order_details_page";
        } else {
            order.setId(id);
            orderService.updateOrderDetails(order);
            return "redirect:/user/sale/orders";
        }
    }
}
