package com.churilovich.bortnik.darya.shop.on.sofa.service.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.churilovich.bortnik.darya.shop.on.sofa.service.constants.RegexValueConstants.EMAIL_REGEX_VALUE;
import static com.churilovich.bortnik.darya.shop.on.sofa.service.constants.ValidationMessageValueConstants.INVALID_EMAIL_FORMAT_MESSAGE;
import static com.churilovich.bortnik.darya.shop.on.sofa.service.constants.ValidationMessageValueConstants.INVALID_EMAIL_SIZE_MESSAGE;

public class UserDTO {
    private Long id;
    @NotNull
    @Size(min = 5, max = 50, message = INVALID_EMAIL_SIZE_MESSAGE)
    @Pattern(regexp = EMAIL_REGEX_VALUE, message = INVALID_EMAIL_FORMAT_MESSAGE)
    private String email;
    private String password;
    private RoleDTO roleDTO;
    private UserInformationDTO userInformationDTO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleDTO getRoleDTO() {
        return roleDTO;
    }

    public void setRoleDTO(RoleDTO roleDTO) {
        this.roleDTO = roleDTO;
    }

    public UserInformationDTO getUserInformationDTO() {
        return userInformationDTO;
    }

    public void setUserInformationDTO(UserInformationDTO userInformationDTO) {
        this.userInformationDTO = userInformationDTO;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", roleDTO=" + roleDTO +
                ", userInformationDTO=" + userInformationDTO +
                '}';
    }
}
