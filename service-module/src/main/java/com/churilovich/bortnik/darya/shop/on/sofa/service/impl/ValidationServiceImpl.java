package com.churilovich.bortnik.darya.shop.on.sofa.service.impl;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.UserProfileRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.UserProfile;
import com.churilovich.bortnik.darya.shop.on.sofa.service.ValidationService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.ValidationServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserDTOLogin;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ValidationServiceImpl implements ValidationService {
    private final UserProfileRepository userProfileRepository;

    @Override
    public boolean isUserHasFirstAndLastNames(UserDTOLogin userDTOLogin) {
        Long id = userDTOLogin.getUserId();
        return userProfileRepository.findByIdIfExist(id)
                .map(this::isFirstAndLastNamesExist)
                .orElseGet(() -> {
                    throw new ValidationServiceException("User profile doesn't exist");
                });
    }

    private Boolean isFirstAndLastNamesExist(UserProfile userProfile) {
        boolean isFirstNameExist = Objects.nonNull(userProfile.getFirstName());
        boolean isLastNameExist = Objects.nonNull(userProfile.getLastName());
        return isFirstNameExist && isLastNameExist;
    }
}
