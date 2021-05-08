package com.churilovich.bortnik.darya.shop.on.sofa.service.model;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.churilovich.bortnik.darya.shop.on.sofa.service.constants.RegexValueConstants.EMAIL_REGEX_VALUE;
import static com.churilovich.bortnik.darya.shop.on.sofa.service.constants.ValidationMessageValueConstants.INVALID_EMAIL_FORMAT_MESSAGE;
import static com.churilovich.bortnik.darya.shop.on.sofa.service.constants.ValidationMessageValueConstants.INVALID_EMAIL_SIZE_MESSAGE;

@Data
@ToString(exclude = "password")
public class UserDTO {
    private Long id;
    @NotNull
    @Size(min = 5, max = 50, message = INVALID_EMAIL_SIZE_MESSAGE)
    @Pattern(regexp = EMAIL_REGEX_VALUE, message = INVALID_EMAIL_FORMAT_MESSAGE)
    private String email;
    private String password;
    private RoleDTO roleDTO;
    private UserInformationDTO userInformationDTO;
}
