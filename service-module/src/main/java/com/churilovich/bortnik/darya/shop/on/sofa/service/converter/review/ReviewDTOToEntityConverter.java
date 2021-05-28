package com.churilovich.bortnik.darya.shop.on.sofa.service.converter.review;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.Review;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.User;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.UserProfile;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ReviewDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserProfileDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ReviewDTOToEntityConverter implements Converter<ReviewDTO, Review> {
    private final Converter<UserProfileDTO, UserProfile> profileConverter;

    @Override
    public Review convert(ReviewDTO reviewDTO) {
        Review review = new Review();
        review.setId(reviewDTO.getId());
        review.setAddDate(reviewDTO.getDate());
        review.setDescription(reviewDTO.getDescription());
        review.setIsShown(reviewDTO.getIsShown());
        User user = review.getUser();
        if (Objects.nonNull(user)) {
            review.getUser().setUserProfile(getConvertedUserProfile(reviewDTO));
        } else {
            user = new User();
            user.setId(reviewDTO.getUserProfileDTO().getId());
            user.setUserProfile(getConvertedUserProfile(reviewDTO));
            review.setUser(user);
        }
        return review;
    }

    private UserProfile getConvertedUserProfile(ReviewDTO reviewDTO) {
        UserProfileDTO userProfileDTO = reviewDTO.getUserProfileDTO();
        return profileConverter.convert(userProfileDTO);
    }
}
