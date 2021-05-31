package com.churilovich.bortnik.darya.shop.on.sofa.service.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class CommentDTO {
    private Long id;
    private LocalDateTime dateAdded;
    @NotBlank(message = "This field must be filled")
    private String description;
    private String userLastName;
    private String userFirstName;
    private Long userId;
    private Long articleId;
}
