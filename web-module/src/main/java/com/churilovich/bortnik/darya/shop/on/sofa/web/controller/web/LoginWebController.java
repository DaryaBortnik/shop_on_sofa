package com.churilovich.bortnik.darya.shop.on.sofa.web.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginWebController {

    @GetMapping("/login")
    public String getLoginPage() {
        return "login_page";
    }

    @GetMapping("/login-error")
    public String getErrorLoginPage(Model model) {
        model.addAttribute("loginError", true);
        return "login_page";
    }
}
