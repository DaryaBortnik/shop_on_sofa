package com.churilovich.bortnik.darya.shop.on.sofa.service;

import com.churilovich.bortnik.darya.shop.on.sofa.service.model.CommentDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserDTOLogin;

import java.util.List;

public interface CommentService {
    List<CommentDTO> findAllByArticleId(Long id);

    Long deleteById(Long id);

    CommentDTO add(CommentDTO comment, UserDTOLogin userDTOLogin);
}
