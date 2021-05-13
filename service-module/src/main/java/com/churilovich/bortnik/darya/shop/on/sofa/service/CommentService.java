package com.churilovich.bortnik.darya.shop.on.sofa.service;

import com.churilovich.bortnik.darya.shop.on.sofa.service.model.CommentDTO;

import java.util.List;

public interface CommentService {
    List<CommentDTO> findAllByArticleId(Long id);
}
