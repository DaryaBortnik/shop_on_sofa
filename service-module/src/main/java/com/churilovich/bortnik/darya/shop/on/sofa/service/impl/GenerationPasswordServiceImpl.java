package com.churilovich.bortnik.darya.shop.on.sofa.service.impl;

import com.churilovich.bortnik.darya.shop.on.sofa.service.GenerationPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

import static com.churilovich.bortnik.darya.shop.on.sofa.service.constants.PasswordGenerateValueConstants.EXCLAMATION_MARK_UNICODE_VALUE;
import static com.churilovich.bortnik.darya.shop.on.sofa.service.constants.PasswordGenerateValueConstants.PASSWORD_LENGTH_VALUE;
import static com.churilovich.bortnik.darya.shop.on.sofa.service.constants.PasswordGenerateValueConstants.RIGHT_CURLY_BRACKET_UNICODE_VALUE;

@Service
public class GenerationPasswordServiceImpl implements GenerationPasswordService {
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public GenerationPasswordServiceImpl(@Lazy PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String generate() {
        return new Random().ints(EXCLAMATION_MARK_UNICODE_VALUE, RIGHT_CURLY_BRACKET_UNICODE_VALUE + 1)
                .limit(PASSWORD_LENGTH_VALUE)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    @Override
    public String encode(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
