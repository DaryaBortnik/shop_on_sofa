package com.churilovich.bortnik.darya.shop.on.sofa.service;

import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserDTOLogin;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserProfileDTO;

public interface UserProfileService {
    UserProfileDTO getUserProfile(UserDTOLogin userDTOLogin);
}
