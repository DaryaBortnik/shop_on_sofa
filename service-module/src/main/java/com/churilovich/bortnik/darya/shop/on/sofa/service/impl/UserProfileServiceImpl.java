package com.churilovich.bortnik.darya.shop.on.sofa.service.impl;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.UserProfileRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.service.UserProfileService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserDTOLogin;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserProfileDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {
    private final UserProfileRepository profileRepository;
    private final ConversionService conversionService;

    @Override
    public UserProfileDTO getUserProfile(UserDTOLogin userDTOLogin) {
        Long id = userDTOLogin.getUserId();
        return profileRepository.findByIdIfExist(id)
                .map(profile -> conversionService.convert(profile, UserProfileDTO.class))
                .orElse(new UserProfileDTO(id));
    }
}
