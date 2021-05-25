package com.churilovich.bortnik.darya.shop.on.sofa.web.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ExceptionController {

    @GetMapping("/400")
    public String error400() {
        return "error_400_page";
    }

    @GetMapping("/403")
    public String error403() {
        return "error_403_page";
    }

    @GetMapping("/404")
    public String error404() {
        return "error_404_page";
    }

    @GetMapping("/409")
    public String error409() {
        return "error_409_page";
    }
}
