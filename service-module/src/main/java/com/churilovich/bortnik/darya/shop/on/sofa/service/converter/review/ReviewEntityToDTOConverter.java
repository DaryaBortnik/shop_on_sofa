package com.churilovich.bortnik.darya.shop.on.sofa.service.converter.review;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.Review;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.UserProfile;
import com.churilovich.bortnik.darya.shop.on.sofa.service.converter.profile.UserProfileEntityToDTOConverter;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ReviewDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserProfileDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ReviewEntityToDTOConverter implements Converter<Review, ReviewDTO> {
    private final UserProfileEntityToDTOConverter userProfileConverter;

    public ReviewEntityToDTOConverter(UserProfileEntityToDTOConverter userProfileConverter) {
        this.userProfileConverter = userProfileConverter;
    }

    @Override
    public ReviewDTO convert(Review review) {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setId(review.getId());
        reviewDTO.setDescription(review.getDescription());
        reviewDTO.setDate(review.getAddDate());
        reviewDTO.setIsShown(review.getIsShown());
        reviewDTO.setUserProfileDTO(getConvertedUserProfile(review));
        return reviewDTO;
    }

    private UserProfileDTO getConvertedUserProfile(Review review) {
        UserProfile userProfile = review.getUser().getUserProfile();
        return userProfileConverter.convert(userProfile);
    }
}
