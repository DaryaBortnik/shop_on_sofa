package com.churilovich.bortnik.darya.shop.on.sofa.service.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ResponseDTO<T> {
    private final T output;
}
