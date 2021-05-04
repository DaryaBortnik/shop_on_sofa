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
public class UserDTOToEntityConverter implements Converter<UserDTO, User> {
    private final Converter<RoleDTO, Role> roleConverter;
    private final Converter<UserInformationDTO, UserInformation> userInformationConverter;

    public UserDTOToEntityConverter(Converter<RoleDTO, Role> roleConverter,
                                    Converter<UserInformationDTO, UserInformation> userInformationConverter) {
        this.roleConverter = roleConverter;
        this.userInformationConverter = userInformationConverter;
    }

    @Override
    public User convert(UserDTO userDTO) {
        Long id = userDTO.getId();
        User user = new User();
        user.setId(id);

        String email = userDTO.getEmail();
        user.setEmail(email);

        String password = userDTO.getPassword();
        user.setPassword(password);

        Role role = roleConverter.convert(userDTO.getRoleDTO());
        user.setRole(role);

        UserInformation userInfo = userInformationConverter.convert(userDTO.getUserInformationDTO());
        user.setUserInformation(userInfo);

        return user;
    }
}
