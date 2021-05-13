package com.churilovich.bortnik.darya.shop.on.sofa.repository;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.UserProfile;

public interface UserProfileRepository extends GenericRepository<Long, UserProfile> {
    UserProfile findByFirstAndLastNames(String firstName, String lastName);
}
