package com.churilovich.bortnik.darya.shop.on.sofa.service.converter.comment;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.Article;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.Comment;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.User;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.CommentDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CommentDTOToEntity implements Converter<CommentDTO, Comment> {
    @Override
    public Comment convert(CommentDTO commentDTO) {
        Comment comment = new Comment();
        LocalDate dateAdded = commentDTO.getDateAdded();
        comment.setDateAdded(dateAdded);
        Article article = new Article();
        Long articleId = commentDTO.getArticleId();
        article.setId(articleId);
        comment.setArticle(article);
        String description = commentDTO.getDescription();
        comment.setDescription(description);
        User user = new User();
        Long userId = commentDTO.getUserId();
        user.setId(userId);
        comment.setUser(user);
        return comment;
    }
}
