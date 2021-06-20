package com.churilovich.bortnik.darya.shop.on.sofa.service.model;

import lombok.Data;

@Data
public class ShopDTO {
    private Long id;
    private String name;
    private String certificate;
    private Boolean isDeleted;
    private UserDTO userDTO;
}
