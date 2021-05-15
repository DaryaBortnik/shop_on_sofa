package com.churilovich.bortnik.darya.shop.on.sofa.repository;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.Comment;

import java.util.List;

public interface CommentRepository extends GenericRepository<Long, Comment> {
    List<Comment> findAllByArticleId(Long id);
}
