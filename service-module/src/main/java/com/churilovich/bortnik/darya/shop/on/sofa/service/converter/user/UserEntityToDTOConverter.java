package com.churilovich.bortnik.darya.shop.on.sofa.service.converter.user;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.Role;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.User;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.UserProfile;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.RoleDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserProfileDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserEntityToDTOConverter implements Converter<User, UserDTO> {
    private final Converter<Role, RoleDTO> roleConverter;
    private final Converter<UserProfile, UserProfileDTO> userProfileConverter;

    public UserEntityToDTOConverter(Converter<Role, RoleDTO> roleConverter,
                                    Converter<UserProfile, UserProfileDTO> userProfileConverter) {
        this.roleConverter = roleConverter;
        this.userProfileConverter = userProfileConverter;
    }

    @Override
    public UserDTO convert(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setRoleDTO(getConvertedRole(user));
        if (Objects.nonNull(user.getUserProfile())) {
            userDTO.setUserProfileDTO(getConvertedUserProfile(user));
        }
        return userDTO;
    }

    private RoleDTO getConvertedRole(User user) {
        Role role = user.getRole();
        return roleConverter.convert(role);
    }

    private UserProfileDTO getConvertedUserProfile(User user) {
        UserProfile userProfile = user.getUserProfile();
        return userProfileConverter.convert(userProfile);
    }
}
