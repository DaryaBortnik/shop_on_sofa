package com.churilovich.bortnik.darya.shop.on.sofa.repository;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.User;

import java.util.List;

public interface UserRepository extends GenericRepository<Long, User> {
    User getByUsername(String username);

    List<User> findAll(Long startNumberOnCurrentPage, Long amountOnOnePage);

    List<User> findByRoleId(Long roleId);
}
