package com.churilovich.bortnik.darya.shop.on.sofa.service.converter.role;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.Role;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.RoleDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.enums.RoleDTOEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RoleEntityToDTOConverter implements Converter<Role, RoleDTO> {
    @Override
    public RoleDTO convert(Role role) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(role.getId());
        roleDTO.setName(getConvertedRoleName(role));
        roleDTO.setDescription(role.getDescription());
        return roleDTO;
    }

    private String getConvertedRoleName(Role role) {
        String roleNameValue = role.getName().name();
        RoleDTOEnum roleDTOName = RoleDTOEnum.valueOf(roleNameValue);
        return roleDTOName.name();
    }
}
