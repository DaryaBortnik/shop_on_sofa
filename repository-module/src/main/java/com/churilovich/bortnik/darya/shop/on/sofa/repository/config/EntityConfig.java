package com.churilovich.bortnik.darya.shop.on.sofa.repository.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@EntityScan(basePackages = "com.churilovich.bortnik.darya.shop.on.sofa.repository.model")
@Configuration
public class EntityConfig {
}
