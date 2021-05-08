package com.churilovich.bortnik.darya.shop.on.sofa.service.converter.user.information;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.UserInformation;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserInformationDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserInformationEntityToDTOConverter implements Converter<UserInformation, UserInformationDTO> {

    @Override
    public UserInformationDTO convert(UserInformation userInfo) {
        UserInformationDTO userInfoDTO = new UserInformationDTO();

        if (Objects.nonNull(userInfo)) {
            Long id = userInfo.getId();

            userInfoDTO.setId(id);

            String firstName = userInfo.getFirstName();
            userInfoDTO.setFirstName(firstName);

            String middleName = userInfo.getMiddleName();
            userInfoDTO.setMiddleName(middleName);

            String lastName = userInfo.getLastName();
            userInfoDTO.setLastName(lastName);
        }
        return userInfoDTO;
    }
}

