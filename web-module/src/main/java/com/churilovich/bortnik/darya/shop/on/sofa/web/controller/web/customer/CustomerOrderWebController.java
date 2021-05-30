package com.churilovich.bortnik.darya.shop.on.sofa.web.controller.web.customer;

import com.churilovich.bortnik.darya.shop.on.sofa.service.OrderService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.ValidationService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.OrderDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserDTOLogin;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.element.PageDTO;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.invoke.MethodHandles;

@Controller
@RequestMapping("/user/customer")
@RequiredArgsConstructor
public class CustomerOrderWebController {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final OrderService orderService;
    private final ValidationService validationService;

    @GetMapping("/orders")
    public String getAllOrders(@RequestParam(defaultValue = "1", value = "current_page") Long currentPageNumber,
                               Model model) {
        PageDTO<OrderDTO> pageWithItems = orderService.getOrdersOnPage(currentPageNumber);
        model.addAttribute("orders", pageWithItems.getList());
        model.addAttribute("page", pageWithItems);
        return "customer_get_all_orders_page";
    }

    @PostMapping("/orders/create")
    public String createOrder(@AuthenticationPrincipal UserDTOLogin userDTOLogin,
                              @RequestParam(required = false, name = "added_item") Long id,
                              @RequestParam(required = false, name = "amount") Long amount) {
        if (validationService.isUserHasPhoneNumberAndFirstName(userDTOLogin)) {
            orderService.add(id, amount, userDTOLogin);
            return "redirect:/user/customer/orders";
        } else {
            return "redirect:/user/profile";
        }
    }
}
