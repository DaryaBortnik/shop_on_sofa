package com.churilovich.bortnik.darya.shop.on.sofa.service.converter.review;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.Review;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ReviewDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserInformationDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ReviewEntityToDTOConverter implements Converter<Review, ReviewDTO> {

    @Override
    public ReviewDTO convert(Review review) {
        Long id = review.getId();
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setId(id);

        String description = review.getDescription();
        reviewDTO.setDescription(description);

        LocalDate addDate = review.getAddDate();
        reviewDTO.setDate(addDate);

        boolean shown = review.isShown();
        reviewDTO.setShown(shown);

        UserInformationDTO userInformationDTO = getConvertedUserInformation(review);
        reviewDTO.setUserInformationDTO(userInformationDTO);

        return reviewDTO;
    }

    private UserInformationDTO getConvertedUserInformation(Review review) {
        Long userId = review.getUser().getUserInformation().getId();
        UserInformationDTO userInformationDTO = new UserInformationDTO();
        userInformationDTO.setId(userId);

        String firstName = review.getUser().getUserInformation().getFirstName();
        userInformationDTO.setFirstName(firstName);

        String middleName = review.getUser().getUserInformation().getMiddleName();
        userInformationDTO.setMiddleName(middleName);

        String lastName = review.getUser().getUserInformation().getLastName();
        userInformationDTO.setLastName(lastName);

        return userInformationDTO;
    }
}
