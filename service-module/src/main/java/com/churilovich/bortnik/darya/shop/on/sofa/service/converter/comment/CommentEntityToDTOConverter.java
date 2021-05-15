package com.churilovich.bortnik.darya.shop.on.sofa.service.converter.comment;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.Comment;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.UserProfile;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.CommentDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CommentEntityToDTOConverter implements Converter<Comment, CommentDTO> {

    @Override
    public CommentDTO convert(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setDateAdded(comment.getDateAdded());
        commentDTO.setDescription(comment.getDescription());
        UserProfile userProfile = comment.getUser().getUserProfile();
        commentDTO.setUserFirstName(userProfile.getFirstName());
        commentDTO.setUserLastName(userProfile.getLastName());
        return commentDTO;
    }
}
