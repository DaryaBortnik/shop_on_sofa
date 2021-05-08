package com.churilovich.bortnik.darya.shop.on.sofa.service.converter.user.information;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.UserInformation;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserInformationDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserInformationDTOToEntity implements Converter<UserInformationDTO, UserInformation> {

    @Override
    public UserInformation convert(UserInformationDTO userInfoDTO) {
        Long id = userInfoDTO.getId();
        UserInformation userInfo = new UserInformation();
        userInfo.setId(id);

        String firstName = userInfoDTO.getFirstName();
        userInfo.setFirstName(firstName);

        String middleName = userInfoDTO.getMiddleName();
        userInfo.setMiddleName(middleName);

        String lastName = userInfoDTO.getLastName();
        userInfo.setLastName(lastName);

        return userInfo;
    }
}
