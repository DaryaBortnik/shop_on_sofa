package com.churilovich.bortnik.darya.shop.on.sofa.service.impl;

import com.churilovich.bortnik.darya.shop.on.sofa.service.UserProfileService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.UserService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserDTOLogin;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserProfileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileServiceImpl implements UserProfileService {
    private final UserService userService;

    @Autowired
    public UserProfileServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserProfileDTO getUserProfile(UserDTOLogin userDTOLogin) {
        Long id = userDTOLogin.getUserId();
        UserDTO user = userService.findById(id);
        return user.getUserProfileDTO();
    }
}
