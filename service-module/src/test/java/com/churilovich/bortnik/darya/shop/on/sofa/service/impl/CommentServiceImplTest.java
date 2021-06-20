package com.churilovich.bortnik.darya.shop.on.sofa.service.impl;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.CommentRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.Comment;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.DeleteByIdServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.CommentDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.core.convert.ConversionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private ConversionService conversionService;

    @InjectMocks
    private CommentServiceImpl commentService;

    @Test
    public void shouldFindAllCommentsWithArticleIdEqualsOne() {
        Long id = 1L;
        List<Comment> expectedComments = new ArrayList<>();
        Comment comment = new Comment();
        comment.setId(id);
        expectedComments.add(comment);
        when(commentRepository.findAllByArticleId(id)).thenReturn(expectedComments);

        List<CommentDTO> expectedCommentsDTO = new ArrayList<>();
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(id);
        expectedCommentsDTO.add(commentDTO);

        when(conversionService.convert(comment, CommentDTO.class)).thenReturn(commentDTO);

        List<CommentDTO> actualComments = commentService.findAllByArticleId(id);

        assertEquals(expectedCommentsDTO.size(), actualComments.size());
    }

    @Test
    public void shouldReturnEmptyListOfCommentsIfCantFindCommentsForArticle() {
        Long id = 1L;
        when(commentRepository.findAllByArticleId(id)).thenReturn(null);
        List<CommentDTO> actualComments = commentService.findAllByArticleId(id);
        assertTrue(actualComments.isEmpty());
    }

    @Test
    public void shouldDeleteById() {
        Long id = 1L;
        Comment comment = new Comment();
        comment.setId(id);
        Optional<Comment> optionalCommit = Optional.of(comment);
        when(commentRepository.findById(id)).thenReturn(optionalCommit);
        Comment expectedComment = optionalCommit.get();
        Long actualId = commentService.deleteById(id);
        assertEquals(expectedComment.getId(), actualId);
    }

    @Test
    public void shouldThrowExceptionWhenDeleteByIdWhichDoesntExist() {
        Long id = 1L;
        when(commentRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(DeleteByIdServiceException.class, () -> commentService.deleteById(id));
    }
}