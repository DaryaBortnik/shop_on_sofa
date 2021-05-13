package com.churilovich.bortnik.darya.shop.on.sofa.service.impl;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.CommentRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.service.CommentService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.CommentDTO;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final ConversionService conversionService;

    public CommentServiceImpl(CommentRepository commentRepository,
                              ConversionService conversionService) {
        this.commentRepository = commentRepository;
        this.conversionService = conversionService;
    }

    @Override
    public List<CommentDTO> findAllByArticleId(Long id) {
        return commentRepository.findAllByArticleId(id).stream()
                .map(comment -> conversionService.convert(comment, CommentDTO.class))
                .collect(Collectors.toList());
    }
}
