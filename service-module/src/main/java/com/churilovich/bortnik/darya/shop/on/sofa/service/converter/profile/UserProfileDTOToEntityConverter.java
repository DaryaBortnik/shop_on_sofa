package com.churilovich.bortnik.darya.shop.on.sofa.service.converter.profile;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.UserProfile;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserProfileDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserProfileDTOToEntityConverter implements Converter<UserProfileDTO, UserProfile> {

    @Override
    public UserProfile convert(UserProfileDTO userProfileDTO) {
        UserProfile userProfile = new UserProfile();
        userProfile.setId(userProfileDTO.getId());
        userProfile.setFirstName(userProfileDTO.getFirstName());
        userProfile.setMiddleName(userProfileDTO.getMiddleName());
        userProfile.setLastName(userProfileDTO.getLastName());
        userProfile.setAddress(userProfileDTO.getAddress());
        userProfile.setPhoneNumber(userProfileDTO.getPhoneNumber());
        return userProfile;
    }
}
