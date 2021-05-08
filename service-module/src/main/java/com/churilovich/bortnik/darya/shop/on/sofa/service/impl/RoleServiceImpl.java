package com.churilovich.bortnik.darya.shop.on.sofa.service.impl;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.RoleRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.Role;
import com.churilovich.bortnik.darya.shop.on.sofa.service.RoleService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.RoleDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final RoleRepository roleRepository;
    private final ConversionService conversionService;

    public RoleServiceImpl(RoleRepository roleRepository, ConversionService conversionService) {
        this.roleRepository = roleRepository;
        this.conversionService = conversionService;
    }

    @Override
    public List<RoleDTO> findAll() {
        logger.info("Finding all roles on service level");
        List<Role> roles = roleRepository.findAll();
        return roles.stream()
                .map(role -> conversionService.convert(role, RoleDTO.class))
                .collect(Collectors.toList());
    }
}
