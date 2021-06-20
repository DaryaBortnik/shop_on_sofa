package com.churilovich.bortnik.darya.shop.on.sofa.service.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.churilovich.bortnik.darya.shop.on.sofa.service.constants.RegexValueConstants.FIRST_NAME_REGEX_VALUE;
import static com.churilovich.bortnik.darya.shop.on.sofa.service.constants.RegexValueConstants.LAST_NAME_REGEX_VALUE;
import static com.churilovich.bortnik.darya.shop.on.sofa.service.constants.RegexValueConstants.MIDDLE_NAME_REGEX_VALUE;
import static com.churilovich.bortnik.darya.shop.on.sofa.service.constants.ValidationMessageValueConstants.INVALID_FIRST_NAME_SIZE;
import static com.churilovich.bortnik.darya.shop.on.sofa.service.constants.ValidationMessageValueConstants.INVALID_LAST_NAME_SIZE;
import static com.churilovich.bortnik.darya.shop.on.sofa.service.constants.ValidationMessageValueConstants.INVALID_MIDDLE_NAME_SIZE;

@Data
@NoArgsConstructor
public class UserProfileDTO {
    private Long id;
    @Size(min = 1, max = 20, message = INVALID_FIRST_NAME_SIZE)
    @Pattern(regexp = FIRST_NAME_REGEX_VALUE)
    private String firstName;
    @Pattern(regexp = MIDDLE_NAME_REGEX_VALUE)
    @Size(min = 1, max = 40, message = INVALID_MIDDLE_NAME_SIZE)
    private String middleName;
    @Size(min = 1, max = 40, message = INVALID_LAST_NAME_SIZE)
    @Pattern(regexp = LAST_NAME_REGEX_VALUE)
    private String lastName;
    @Pattern(regexp = "^([A-z]*\\s)?([A-z]+,)\\s([A-z]+,)\\s([A-z]+)\\s([A-z]+,)\\s([0-9]+/[0-9]*/*[0-9]+)$")
    private String address;
    @Pattern(regexp = "^\\+?(\\d{1,3})?[- .]?\\(?(?:\\d{2,3})\\)?[- .]?\\d\\d\\d[- .]?\\d\\d\\d\\d$")
    private String phoneNumber;

    public UserProfileDTO(Long id) {
        this.id = id;
    }
}
