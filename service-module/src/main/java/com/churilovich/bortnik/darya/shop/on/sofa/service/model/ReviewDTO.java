package com.churilovich.bortnik.darya.shop.on.sofa.service.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewDTO {
    private Long id;
    private String description;
    private LocalDateTime date;
    private Boolean isShown = false;
    private UserProfileDTO userProfileDTO;
}
