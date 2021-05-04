package com.churilovich.bortnik.darya.shop.on.sofa.service.converter.user;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.Role;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.User;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.UserInformation;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.RoleDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserInformationDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserEntityToDTOConverter implements Converter<User, UserDTO> {
    private final Converter<Role, RoleDTO> roleConverter;
    private final Converter<UserInformation, UserInformationDTO> userInformationConverter;

    public UserEntityToDTOConverter(Converter<Role, RoleDTO> roleConverter,
                                    Converter<UserInformation, UserInformationDTO> userInformationConverter) {
        this.roleConverter = roleConverter;
        this.userInformationConverter = userInformationConverter;
    }

    @Override
    public UserDTO convert(User user) {
        Long id = user.getId();
        UserDTO userDTO = new UserDTO();
        userDTO.setId(id);

        String email = user.getEmail();
        userDTO.setEmail(email);

        String password = user.getPassword();
        userDTO.setPassword(password);

        RoleDTO roleDTO = roleConverter.convert(user.getRole());
        userDTO.setRoleDTO(roleDTO);

        UserInformationDTO userInfoDTO = userInformationConverter.convert(user.getUserInformation());
        userDTO.setUserInformationDTO(userInfoDTO);

        return userDTO;
    }
}
