package com.churilovich.bortnik.darya.shop.on.sofa.service;

public interface GenerationPasswordService {
    String generate();

    String encode(String password);

    boolean matches(String rawPassword, String encodedPassword);
}
