package com.churilovich.bortnik.darya.shop.on.sofa.service.impl;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.ArticleRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.Article;
import com.churilovich.bortnik.darya.shop.on.sofa.service.CommentService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.DeleteByIdServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.GetByParameterServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ArticleDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.CommentDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArticleServiceImplTest {
    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private CommentService commentService;
    @Mock
    private ConversionService conversionService;
    @InjectMocks
    private ArticleServiceImpl articleService;

    @Test
    public void shouldFindById() {
        Long id = 1L;
        Article article = new Article();
        article.setId(id);
        Optional<Article> optionalArticle = Optional.of(article);
        when(articleRepository.findById(id)).thenReturn(optionalArticle);
        ArticleDTO expectedArticleDTO = new ArticleDTO();
        expectedArticleDTO.setId(id);
        when(conversionService.convert(optionalArticle.get(), ArticleDTO.class)).thenReturn(expectedArticleDTO);
        ArticleDTO actualArticle = articleService.findById(id);
        assertEquals(expectedArticleDTO.getId(), actualArticle.getId());
    }

    @Test
    public void shouldThrownExceptionIfCantFindById() {
        Long id = 1L;
        when(articleRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(GetByParameterServiceException.class, () -> articleService.findById(id));
    }

    @Test
    public void shouldFindAllArticles() {
        List<Article> articles = new ArrayList<>();
        Long id = 1L;
        Article article = new Article();
        article.setId(id);
        articles.add(article);
        when(articleRepository.findAll()).thenReturn(articles);
        List<ArticleDTO> expectedArticles = new ArrayList<>();
        ArticleDTO expectedArticleDTO = new ArticleDTO();
        expectedArticleDTO.setId(id);
        when(conversionService.convert(article, ArticleDTO.class)).thenReturn(expectedArticleDTO);
        expectedArticles.add(expectedArticleDTO);
        List<ArticleDTO> actualArticles = articleService.findAll();
        assertEquals(expectedArticles.size(), actualArticles.size());
    }

    @Test
    public void shouldReturnEmptyListIfCantFindArticles() {
        List<ArticleDTO> actualArticles = articleService.findAll();
        assertTrue(actualArticles.isEmpty());
    }

    @Test
    public void shouldDeleteById() {
        Long id = 1L;
        Article article = new Article();
        article.setId(id);
        Optional<Article> optionalArticle = Optional.of(article);
        when(articleRepository.findById(id)).thenReturn(optionalArticle);
        Article expectedArticle = optionalArticle.get();
        Long actualId = articleService.deleteById(id);
        assertEquals(expectedArticle.getId(), actualId);
    }

    @Test
    public void shouldThrownExceptionIfCantFindArticleIdWhenDeletingItById() {
        Long id = 1L;
        when(articleRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(DeleteByIdServiceException.class, () -> articleService.deleteById(id));
    }

    @Test
    public void shouldGetArticleWithComments() {
        Long id = 1L;
        Article article = new Article();
        article.setId(id);
        Optional<Article> optionalArticle = Optional.of(article);
        when(articleRepository.findById(id)).thenReturn(optionalArticle);
        ArticleDTO expectedArticleDTO = new ArticleDTO();
        expectedArticleDTO.setId(id);
        when(conversionService.convert(optionalArticle.get(), ArticleDTO.class)).thenReturn(expectedArticleDTO);
        List<CommentDTO> commentsDTO = new ArrayList<>();
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(id);
        when(commentService.findAllByArticleId(id)).thenReturn(commentsDTO);
        expectedArticleDTO.setComments(commentsDTO);

        ArticleDTO withComments = articleService.getWithComments(id);
        assertEquals(expectedArticleDTO.getComments().size(), withComments.getComments().size());
    }

    @Test
    public void shouldGetArticleWithoutComments() {
        Long id = 1L;
        Article article = new Article();
        article.setId(id);
        Optional<Article> optionalArticle = Optional.of(article);
        when(articleRepository.findById(id)).thenReturn(optionalArticle);
        ArticleDTO expectedArticleDTO = new ArticleDTO();
        expectedArticleDTO.setId(id);
        when(conversionService.convert(optionalArticle.get(), ArticleDTO.class)).thenReturn(expectedArticleDTO);
        ArticleDTO actualArticle = articleService.getWithComments(id);
        assertTrue(actualArticle.getComments().isEmpty());
    }

    @Test
    public void shouldUpdateArticle() {
        Long id = 1L;
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(id);
        articleDTO.setName("name");
        articleDTO.setShortDescription("short description");
        articleDTO.setFullDescription("full description");

        Article article = new Article();
        article.setId(id);
        article.setName("name from db");
        article.setShortDescription("short from db");
        Optional<Article> optionalArticle = Optional.of(article);
        when(articleRepository.findById(id)).thenReturn(optionalArticle);

        Article articleFromDB = optionalArticle.get();
        articleFromDB.setId(articleDTO.getId());
        articleFromDB.setName(articleDTO.getName());
        articleFromDB.setShortDescription(articleDTO.getShortDescription());
        articleFromDB.setFullDescription(articleDTO.getFullDescription());
        articleFromDB.setDateAdded(LocalDate.now());

        articleRepository.merge(articleFromDB);

        ArticleDTO mergedArticleDTO = new ArticleDTO();
        mergedArticleDTO.setId(id);
        mergedArticleDTO.setName("name");
        mergedArticleDTO.setShortDescription("short description");
        mergedArticleDTO.setFullDescription("full description");
        when(conversionService.convert(articleFromDB, ArticleDTO.class)).thenReturn(mergedArticleDTO);

        ArticleDTO actualArticleDTO = articleService.updateArticle(articleDTO);
        assertEquals(mergedArticleDTO.getName(), actualArticleDTO.getName());
    }
}