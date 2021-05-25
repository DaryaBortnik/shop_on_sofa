package com.churilovich.bortnik.darya.shop.on.sofa.service.impl;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.CommentRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.service.CommentService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.DeleteByIdServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.CommentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final ConversionService conversionService;

    @Override
    public List<CommentDTO> findAllByArticleId(Long id) {
        return Optional.ofNullable(commentRepository.findAllByArticleId(id)).stream()
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
}
