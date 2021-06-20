package com.churilovich.bortnik.darya.shop.on.sofa.repository;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.Role;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.enums.RoleEnum;

import java.util.List;

public interface RoleRepository extends GenericRepository<Long, Role> {
    Role findByName(RoleEnum name);

    List<Role> findForRegistration();
}
