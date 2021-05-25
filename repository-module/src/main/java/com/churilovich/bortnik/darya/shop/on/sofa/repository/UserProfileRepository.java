package com.churilovich.bortnik.darya.shop.on.sofa.repository;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.UserProfile;

import java.util.Optional;

public interface UserProfileRepository extends GenericRepository<Long, UserProfile> {
    UserProfile findByFirstAndLastNames(String firstName, String lastName);

    Optional<UserProfile> findByIdIfExist(Long id);
}
