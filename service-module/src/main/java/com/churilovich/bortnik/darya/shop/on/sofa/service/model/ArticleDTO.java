package com.churilovich.bortnik.darya.shop.on.sofa.service.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class ArticleDTO {
    private Long id;
    private LocalDate dateAdded;
    private String name;
    private String shortDescription;
    private String fullDescription;
    private String userLastName;
    private String userFirstName;
    private List<CommentDTO> comments = new ArrayList<>();
}
