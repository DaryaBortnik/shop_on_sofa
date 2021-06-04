package com.churilovich.bortnik.darya.shop.on.sofa.service.impl;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.RoleRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.Role;
import com.churilovich.bortnik.darya.shop.on.sofa.service.RoleService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.GetByParameterServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.RoleDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final ConversionService conversionService;

    @Override
    public List<RoleDTO> findAll() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream()
                .map(role -> conversionService.convert(role, RoleDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<RoleDTO> findForRegistration() {
        List<Role> roles = roleRepository.findForRegistration();
        return roles.stream()
                .map(role -> conversionService.convert(role, RoleDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public RoleDTO findById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() ->
                        new GetByParameterServiceException("Can't get item with id on service level : id = " + id));
        return conversionService.convert(role, RoleDTO.class);
    }
}
