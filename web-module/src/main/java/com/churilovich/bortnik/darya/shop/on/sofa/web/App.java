package com.churilovich.bortnik.darya.shop.on.sofa.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(scanBasePackages = {"com.churilovich.bortnik.darya.shop.on.sofa.repository",
        "com.churilovich.bortnik.darya.shop.on.sofa.service", "com.churilovich.bortnik.darya.shop.on.sofa.web"},
        exclude = UserDetailsServiceAutoConfiguration.class)
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
