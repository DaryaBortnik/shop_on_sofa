package com.churilovich.bortnik.darya.shop.on.sofa.service;

import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserDTOLogin;

public interface ValidationService {
    boolean isUserHasFirstAndLastNames(UserDTOLogin userDTOLogin);
}
