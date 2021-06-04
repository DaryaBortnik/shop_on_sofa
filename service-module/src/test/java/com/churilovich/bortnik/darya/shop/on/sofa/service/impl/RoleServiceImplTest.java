package com.churilovich.bortnik.darya.shop.on.sofa.service.impl;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.RoleRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.Role;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.RoleDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private ConversionService conversionService;
    @InjectMocks
    private RoleServiceImpl roleService;

    @Test
    public void shouldFindAllRoles() {
        List<Role> roles = new ArrayList<>();
        Long id = 1L;
        Role role = new Role();
        role.setId(id);
        roles.add(role);
        when(roleRepository.findAll()).thenReturn(roles);
        List<RoleDTO> expectedRoles = new ArrayList<>();
        RoleDTO expectedRoleDTO = new RoleDTO();
        expectedRoleDTO.setId(id);
        when(conversionService.convert(role, RoleDTO.class)).thenReturn(expectedRoleDTO);
        expectedRoles.add(expectedRoleDTO);
        List<RoleDTO> actualRoles = roleService.findAll();
        assertEquals(expectedRoles.size(), actualRoles.size());
    }

    @Test
    public void shouldReturnEmptyListIfCantFindRoles() {
        List<RoleDTO> actualRoles = roleService.findAll();
        assertTrue(actualRoles.isEmpty());
    }
}