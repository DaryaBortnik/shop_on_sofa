package com.churilovich.bortnik.darya.shop.on.sofa.service.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class CommentDTO {
    private Long id;
    private LocalDate dateAdded;
    @NotBlank(message = "This field must be filled")
    private String description;
    private String userLastName;
    private String userFirstName;
}
