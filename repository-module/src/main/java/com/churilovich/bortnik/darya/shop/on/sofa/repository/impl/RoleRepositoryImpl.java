package com.churilovich.bortnik.darya.shop.on.sofa.repository.impl;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.RoleRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.Role;
import org.springframework.stereotype.Repository;

@Repository
public class RoleRepositoryImpl extends GenericRepositoryImpl<Long, Role> implements RoleRepository {
}
