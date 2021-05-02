package com.churilovich.bortnik.darya.shop.on.sofa.service.converter.role;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.Role;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.enums.RoleEnum;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.RoleDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class RoleDTOToEntity implements Converter<RoleDTO, Role> {
    @Override
    public Role convert(RoleDTO roleDTO) {
        Long id = roleDTO.getId();
        Role role = new Role();
        role.setId(id);
        if (Objects.nonNull(roleDTO.getName())) {
            RoleEnum roleName = RoleEnum.valueOf(roleDTO.getName());
            role.setName(roleName);
        }
        if (Objects.nonNull(roleDTO.getDescription())) {
            String description = roleDTO.getDescription();
            role.setDescription(description);
        }
        return role;
    }
}
