package com.churilovich.bortnik.darya.shop.on.sofa.service.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReviewDTO {
    private Long id;
    private String description;
    private LocalDate date;
    private Boolean isShown = false;
    private UserInformationDTO userInformationDTO;
}
