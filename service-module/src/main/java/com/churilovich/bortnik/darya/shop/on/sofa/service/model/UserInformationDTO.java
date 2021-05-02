package com.churilovich.bortnik.darya.shop.on.sofa.service.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.churilovich.bortnik.darya.shop.on.sofa.service.constants.RegexValueConstants.FIRST_NAME_REGEX_VALUE;
import static com.churilovich.bortnik.darya.shop.on.sofa.service.constants.RegexValueConstants.LAST_NAME_REGEX_VALUE;
import static com.churilovich.bortnik.darya.shop.on.sofa.service.constants.RegexValueConstants.MIDDLE_NAME_REGEX_VALUE;
import static com.churilovich.bortnik.darya.shop.on.sofa.service.constants.ValidationMessageValueConstants.INVALID_FIRST_NAME_SIZE;
import static com.churilovich.bortnik.darya.shop.on.sofa.service.constants.ValidationMessageValueConstants.INVALID_LAST_NAME_SIZE;
import static com.churilovich.bortnik.darya.shop.on.sofa.service.constants.ValidationMessageValueConstants.INVALID_MIDDLE_NAME_SIZE;

public class UserInformationDTO {
    private Long id;
    @NotNull
    @Size(min = 1, max = 20, message = INVALID_FIRST_NAME_SIZE)
    @Pattern(regexp = FIRST_NAME_REGEX_VALUE)
    private String firstName;
    @Pattern(regexp = MIDDLE_NAME_REGEX_VALUE)
    @Size(min = 1, max = 40, message = INVALID_MIDDLE_NAME_SIZE)
    private String middleName;
    @NotNull
    @Size(min = 1, max = 40, message = INVALID_LAST_NAME_SIZE)
    @Pattern(regexp = LAST_NAME_REGEX_VALUE)
    private String lastName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return firstName + " " + middleName + " " + lastName;
    }
}
