package com.churilovich.bortnik.darya.shop.on.sofa.service.converter.role;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.Role;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.enums.RoleEnum;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.RoleDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.enums.RoleDTOEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RoleEntityToDTOConverter implements Converter<Role, RoleDTO> {
    @Override
    public RoleDTO convert(Role role) {
        Long id = role.getId();
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(id);

        RoleDTOEnum roleDTOName = getConvertedRoleName(role);
        roleDTO.setName(roleDTOName.name());

        String description = role.getDescription();
        roleDTO.setDescription(description);

        return roleDTO;
    }

    private RoleDTOEnum getConvertedRoleName(Role role) {
        RoleEnum roleName = role.getName();
        String roleNameValue = roleName.name();
        return RoleDTOEnum.valueOf(roleNameValue);
    }
}
