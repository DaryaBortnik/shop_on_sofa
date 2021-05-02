package com.churilovich.bortnik.darya.shop.on.sofa.web.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.invoke.MethodHandles;

@Controller
@RequestMapping("/login")
public class LoginWebController {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    @GetMapping
    public String getLoginPage() {
        logger.info("Getting login page");
        try {
            return "login_page";
        } catch (UsernameNotFoundException e) {
            logger.error(e.getMessage(), e);
            return "error_page";
        }
    }
}
