package com.churilovich.bortnik.darya.shop.on.sofa.service.converter.profile;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.UserProfile;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserProfileDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserProfileEntityToDTOConverter implements Converter<UserProfile, UserProfileDTO> {

    @Override
    public UserProfileDTO convert(UserProfile userProfile) {
        UserProfileDTO userProfileDTO = new UserProfileDTO();
        userProfileDTO.setId(userProfile.getId());
        userProfileDTO.setFirstName(userProfile.getFirstName());
        userProfileDTO.setMiddleName(userProfile.getMiddleName());
        userProfileDTO.setLastName(userProfile.getLastName());
        userProfileDTO.setAddress(userProfile.getAddress());
        userProfileDTO.setPhoneNumber(userProfile.getPhoneNumber());
        return userProfileDTO;
    }
}

