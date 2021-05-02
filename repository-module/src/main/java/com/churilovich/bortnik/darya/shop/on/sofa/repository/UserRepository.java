package com.churilovich.bortnik.darya.shop.on.sofa.repository;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.User;

import java.util.List;

public interface UserRepository extends GenericRepository<Long, User> {
    User getByUsername(String username);

    List<User> findAll(Long startNumberOnCurrentPage, Long amountOnOnePage);
}