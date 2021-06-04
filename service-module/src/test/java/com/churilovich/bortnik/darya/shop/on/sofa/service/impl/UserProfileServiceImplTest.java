package com.churilovich.bortnik.darya.shop.on.sofa.service.impl;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.UserProfileRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.UserProfile;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.RoleDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserDTOLogin;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserProfileDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserProfileServiceImplTest {
    @Mock
    private UserProfileRepository profileRepository;
    @Mock
    private ConversionService conversionService;
    @InjectMocks
    private UserProfileServiceImpl userProfileService;

    @Test
    public void shouldGetUserProfile() {
        Long id = 1L;
        UserProfile userProfile = new UserProfile();
        userProfile.setId(id);
        when(profileRepository.findByIdIfExist(id)).thenReturn(Optional.of(userProfile));
        UserProfileDTO expectedUserProfileDTO = new UserProfileDTO();
        expectedUserProfileDTO.setId(id);
        UserProfile userProfileFromDB = Optional.of(userProfile).get();
        when(conversionService.convert(userProfileFromDB, UserProfileDTO.class)).thenReturn(expectedUserProfileDTO);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(id);
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(id);
        roleDTO.setName("SALE_USER");
        userDTO.setRoleDTO(roleDTO);
        UserDTOLogin userDTOLogin = new UserDTOLogin(userDTO);
        UserProfileDTO actualUserProfile = userProfileService.getUserProfile(userDTOLogin);
        assertEquals(expectedUserProfileDTO.getId(), actualUserProfile.getId());
    }

    @Test
    public void shouldGetNewUserProfile() {
        Long id = 1L;
        when(profileRepository.findByIdIfExist(id)).thenReturn(Optional.empty());
        UserDTO userDTO = new UserDTO();
        userDTO.setId(id);
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(id);
        roleDTO.setName("SALE_USER");
        userDTO.setRoleDTO(roleDTO);
        UserDTOLogin userDTOLogin = new UserDTOLogin(userDTO);
        UserProfileDTO actualUserProfile = userProfileService.getUserProfile(userDTOLogin);
        assertNotNull(actualUserProfile);
    }
}