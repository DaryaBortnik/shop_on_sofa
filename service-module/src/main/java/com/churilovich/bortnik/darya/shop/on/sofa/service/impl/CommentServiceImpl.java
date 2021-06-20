package com.churilovich.bortnik.darya.shop.on.sofa.service.impl;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.CommentRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.Comment;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.UserProfile;
import com.churilovich.bortnik.darya.shop.on.sofa.service.CommentService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.UserProfileService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.DeleteByIdServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.CommentDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserDTOLogin;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserProfileDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserProfileService userProfileService;
    private final ConversionService conversionService;

    @Override
    public List<CommentDTO> findAllByArticleId(Long id) {
        return commentRepository.findAllByArticleId(id).stream()
                .map(comment -> conversionService.convert(comment, CommentDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Long deleteById(Long id) {
        commentRepository.findById(id)
                .ifPresentOrElse(commentRepository::remove, () -> {
                    throw new DeleteByIdServiceException("Can't deleteById comment by id on service level because can't " +
                            "found comment with id = " + id);
                });
        return id;
    }

    @Override
    @Transactional
    public CommentDTO add(CommentDTO commentDTO, UserDTOLogin userDTOLogin) {
        UserProfileDTO userProfile = userProfileService.getUserProfile(userDTOLogin);
        commentDTO.setUserFirstName(userProfile.getFirstName());
        commentDTO.setUserLastName(userProfile.getLastName());
        commentDTO.setUserId(userDTOLogin.getUserId());
        commentDTO.setDateAdded(LocalDateTime.now());
        Comment comment = conversionService.convert(commentDTO, Comment.class);
        comment.getUser().setUserProfile(conversionService.convert(userProfile, UserProfile.class));
        commentRepository.persist(comment);
        return conversionService.convert(comment, CommentDTO.class);
    }
}
