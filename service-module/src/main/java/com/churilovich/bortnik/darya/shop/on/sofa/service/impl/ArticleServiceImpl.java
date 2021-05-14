package com.churilovich.bortnik.darya.shop.on.sofa.service.impl;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.ArticleRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.exception.GetEntitiesAmountRepositoryException;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.exception.PersistEntityRepositoryException;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.Article;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.User;
import com.churilovich.bortnik.darya.shop.on.sofa.service.ArticleService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.CommentService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.PaginationService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.UserService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.AddServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.DeleteByIdServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.GetByParameterServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.GetArticlesServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ArticleDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.CommentDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.PageDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.churilovich.bortnik.darya.shop.on.sofa.service.constants.PaginationValueConstants.AMOUNT_ON_ONE_PAGE;

@Service
public class ArticleServiceImpl implements ArticleService {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final ArticleRepository articleRepository;
    private final CommentService commentService;
    private final UserService userService;
    private final ConversionService conversionService;
    private final PaginationService paginationService;

    public ArticleServiceImpl(ArticleRepository articleRepository,
                              CommentService commentService,
                              UserService userService,
                              ConversionService conversionService,
                              PaginationService paginationService) {
        this.articleRepository = articleRepository;
        this.commentService = commentService;
        this.userService = userService;
        this.conversionService = conversionService;
        this.paginationService = paginationService;
    }

    @Override
    @Transactional
    public PageDTO<ArticleDTO> getArticlesOnPage(Long currentPageNumber) {
        try {
            Long amountOfArticles = articleRepository.getAmountOfEntities();
            Long amountOfPages = paginationService.getAmountOfPagesForElements(amountOfArticles, AMOUNT_ON_ONE_PAGE);
            return buildPageWithArticles(currentPageNumber, amountOfPages);
        } catch (GetEntitiesAmountRepositoryException e) {
            logger.error(e.getMessage(), e);
            throw new GetArticlesServiceException("Can't get all reviews on current page on service level " +
                    "due to impossibility to get total amount of reviews", e);
        }
    }

    @Override
    @Transactional
    public ArticleDTO findById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() ->
                        new GetByParameterServiceException("Can't get news with id on service level : id = " + id));
        return conversionService.convert(article, ArticleDTO.class);
    }

    @Override
    @Transactional
    public List<ArticleDTO> findAll() {
        List<Article> articles = articleRepository.findAll();
        return articles.stream()
                .map(article -> conversionService.convert(article, ArticleDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void add(ArticleDTO articleDTO) {
        try {
            UserDTO userDTO = getUserOfArticle(articleDTO);
            User user = conversionService.convert(userDTO, User.class);
            Article article = conversionService.convert(articleDTO, Article.class);
            Optional.ofNullable(article)
                    .ifPresentOrElse(currentArticle -> addArticleWithUser(user, currentArticle), () -> {
                        throw new AddServiceException("Can't add new article on service level because user of articles doesn't exist");
                    });
        } catch (PersistEntityRepositoryException e) {
            logger.error(e.getMessage(), e);
            throw new AddServiceException("Can't add new article on service level because its existing", e);
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        articleRepository.findById(id)
                .ifPresentOrElse(articleRepository::remove, () -> {
                    throw new DeleteByIdServiceException("Can't deleteById article by id on service level because can't " +
                            "found article with id = " + id);
                });
    }

    @Override
    public ArticleDTO getWithComments(Long id) {
        ArticleDTO article = findById(id);
        List<CommentDTO> comments = commentService.findAllByArticleId(id);
        article.setComments(comments);
        return article;
    }

    private UserDTO getUserOfArticle(ArticleDTO articleDTO) {
        String firstName = articleDTO.getUserFirstName();
        String lastName = articleDTO.getUserLastName();
        return userService.getByFirstAndLastNames(firstName, lastName);
    }

    private PageDTO<ArticleDTO> buildPageWithArticles(Long currentPageNumber, Long amountOfPages) {
        PageDTO<ArticleDTO> page = new PageDTO<>();
        page.setPagesAmount(amountOfPages);
        currentPageNumber = paginationService.getCurrentPageNumber(currentPageNumber, amountOfPages);
        Long startNumberOnCurrentPage = paginationService.getElementPosition(currentPageNumber, AMOUNT_ON_ONE_PAGE);
        List<Article> articles = articleRepository.findAll(startNumberOnCurrentPage, AMOUNT_ON_ONE_PAGE);
        List<ArticleDTO> articleDTO = getArticleDTO(articles);
        page.getList().addAll(articleDTO);
        return page;
    }

    private List<ArticleDTO> getArticleDTO(List<Article> articles) {
        return articles.stream()
                .map(article -> conversionService.convert(article, ArticleDTO.class))
                .collect(Collectors.toList());
    }

    private void addArticleWithUser(User user, Article article) {
        article.setUser(user);
        articleRepository.persist(article);
    }

}
