package com.churilovich.bortnik.darya.shop.on.sofa.service.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CommentDTO {
    private Long id;
    private LocalDate dateAdded;
    private String description;
    private String userLastName;
    private String userFirstName;
}
