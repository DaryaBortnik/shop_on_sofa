package com.churilovich.bortnik.darya.shop.on.sofa.service.converter.role;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.Role;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.enums.RoleEnum;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.RoleDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class RoleDTOToEntityConverter implements Converter<RoleDTO, Role> {

    @Override
    public Role convert(RoleDTO roleDTO) {
        Role role = new Role();
        role.setId(roleDTO.getId());
        String name = roleDTO.getName();
        if (Objects.nonNull(name)) {
            role.setName(RoleEnum.valueOf(name));
        }
        String description = roleDTO.getDescription();
        if (Objects.nonNull(description)) {
            role.setDescription(description);
        }
        return role;
    }
}
