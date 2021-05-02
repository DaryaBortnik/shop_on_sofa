package com.churilovich.bortnik.darya.shop.on.sofa.service.impl;

import com.churilovich.bortnik.darya.shop.on.sofa.service.GenerationPasswordService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.Random;

import static com.churilovich.bortnik.darya.shop.on.sofa.service.constants.PasswordGenerateValueConstants.EXCLAMATION_MARK_UNICODE_VALUE;
import static com.churilovich.bortnik.darya.shop.on.sofa.service.constants.PasswordGenerateValueConstants.PASSWORD_LENGTH_VALUE;
import static com.churilovich.bortnik.darya.shop.on.sofa.service.constants.PasswordGenerateValueConstants.RIGHT_CURLY_BRACKET_UNICODE_VALUE;

@Service
public class GenerationPasswordServiceImpl implements GenerationPasswordService {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public GenerationPasswordServiceImpl(@Lazy PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String generate() {
        logger.info("Generating random password on service level");
        return new Random().ints(EXCLAMATION_MARK_UNICODE_VALUE, RIGHT_CURLY_BRACKET_UNICODE_VALUE + 1)
                .limit(PASSWORD_LENGTH_VALUE)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    @Override
    public String encode(String password) {
        return passwordEncoder.encode(password);
    }
}
