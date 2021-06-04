package com.churilovich.bortnik.darya.shop.on.sofa.service;

import com.churilovich.bortnik.darya.shop.on.sofa.service.model.RoleDTO;

import java.util.List;

public interface RoleService {
    List<RoleDTO> findAll();

    List<RoleDTO> findForRegistration();

    RoleDTO findById(Long id);
}
