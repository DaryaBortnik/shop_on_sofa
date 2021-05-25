package com.churilovich.bortnik.darya.shop.on.sofa.service.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class ArticleDTO {
    private Long id;
    private LocalDate dateAdded;
    @NotBlank(message = "This field must be filled")
    @Size(max = 40, message = "Invalid size: max size = 40")
    private String name;
    @Size(max = 200, message = "Invalid size: max size = 200")
    private String shortDescription;
    @NotBlank(message = "This field must be filled")
    @Size(max = 1000,  message = "Invalid size: max size = 1000")
    private String fullDescription;
    private UserDTO user;
    private List<CommentDTO> comments = new ArrayList<>();
}
