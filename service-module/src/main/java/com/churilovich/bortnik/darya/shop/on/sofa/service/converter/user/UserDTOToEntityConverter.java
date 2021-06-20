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
public class UserDTOToEntityConverter implements Converter<UserDTO, User> {
    private final Converter<RoleDTO, Role> roleConverter;
    private final Converter<UserProfileDTO, UserProfile> userProfileConverter;

    public UserDTOToEntityConverter(Converter<RoleDTO, Role> roleConverter,
                                    Converter<UserProfileDTO, UserProfile> userProfileConverter) {
        this.roleConverter = roleConverter;
        this.userProfileConverter = userProfileConverter;
    }

    @Override
    public User convert(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setRole(getConvertedRole(userDTO));
        user.setUserProfile(getConvertedUserProfile(userDTO));
        return user;
    }

    private Role getConvertedRole(UserDTO userDTO) {
        RoleDTO roleDTO = userDTO.getRoleDTO();
        return roleConverter.convert(roleDTO);
    }

    private UserProfile getConvertedUserProfile(UserDTO userDTO) {
        UserProfileDTO userProfileDTO = userDTO.getUserProfileDTO();
        if (Objects.nonNull(userProfileDTO)) {
            return userProfileConverter.convert(userProfileDTO);
        } else {
            UserProfile userProfile = new UserProfile();
            userProfile.setId(userDTO.getId());
            return userProfile;
        }
    }
}
