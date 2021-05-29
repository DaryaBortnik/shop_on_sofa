package com.churilovich.bortnik.darya.shop.on.sofa.service.impl;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.ArticleRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.exception.GetEntitiesAmountRepositoryException;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.exception.PersistEntityRepositoryException;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.Article;
import com.churilovich.bortnik.darya.shop.on.sofa.service.ArticleService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.CommentService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.PaginationService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.UserService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.AddServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.DeleteByIdServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.GetByParameterServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.GetOnPageServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.UpdateParameterServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ArticleDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.CommentDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ShopDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserDTOLogin;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.element.PageDTO;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.churilovich.bortnik.darya.shop.on.sofa.service.constants.PaginationValueConstants.AMOUNT_ON_ONE_PAGE;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final ArticleRepository articleRepository;
    private final CommentService commentService;
    private final UserService userService;
    private final ConversionService conversionService;
    private final PaginationService<ArticleRepository> paginationService;

    @Override
    @Transactional
    public PageDTO<ArticleDTO> getAllOnPage(Long currentPageNumber) {
        try {
            Long amountOfPages = paginationService.getAmountOfPages(articleRepository);
            return buildPageWithArticles(currentPageNumber, amountOfPages);
        } catch (GetEntitiesAmountRepositoryException e) {
            logger.error(e.getMessage(), e);
            throw new GetOnPageServiceException("Can't get all reviews on current page on service level " +
                    "due to impossibility to get total amount of reviews", e);
        }
    }

    @Override
    public List<ArticleDTO> findByShop(ShopDTO shop) {
        Long userSaleId = shop.getUserDTO().getId();
        return getArticlesByUserSaleId(userSaleId);
    }

    private List<ArticleDTO> getArticlesByUserSaleId(Long userSaleId) {
        List<Article> articles = articleRepository.getByUserSaleId(userSaleId);
        return articles.stream()
                .map(article -> conversionService.convert(article, ArticleDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PageDTO<ArticleDTO> getBySaleUserIdOnPage(Long currentPageNumber, UserDTOLogin userDTOLogin) {
        try {
            Long amountOfPages = paginationService.getAmountOfPages(articleRepository);
            Long userId = userDTOLogin.getUserId();
            return buildPageWithArticlesByUserId(currentPageNumber, amountOfPages, userId);
        } catch (GetEntitiesAmountRepositoryException e) {
            logger.error(e.getMessage(), e);
            throw new GetOnPageServiceException("Can't get all reviews on current page on service level " +
                    "due to impossibility to get total amount of reviews", e);
        }
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
    public ArticleDTO findById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() ->
                        new GetByParameterServiceException("Can't get article with id on service level : id = " + id));
        return conversionService.convert(article, ArticleDTO.class);
    }

    @Override
    @Transactional
    public ArticleDTO add(ArticleDTO article, UserDTOLogin userDTOLogin) {
        Long userId = userDTOLogin.getUserId();
        UserDTO user = userService.findById(userId);
        article.setUser(user);
        article.setDateAdded(LocalDate.now());
        return addWithUser(article);
    }

    @Override
    @Transactional
    public Long deleteById(Long id) {
        articleRepository.findById(id)
                .ifPresentOrElse(articleRepository::remove, () -> {
                    throw new DeleteByIdServiceException("Can't deleteById article by id on service level because can't " +
                            "found article with id = " + id);
                });
        return id;
    }

    @Override
    @Transactional
    public ArticleDTO getWithComments(Long id) {
        ArticleDTO article = findById(id);
        List<CommentDTO> comments = commentService.findAllByArticleId(id);
        article.getComments().addAll(comments);
        return article;
    }

    @Override
    @Transactional
    public ArticleDTO updateArticle(ArticleDTO articleDTO) {
        Long id = articleDTO.getId();
        Article updatedMergedArticle = articleRepository.findById(id)
                .map(article -> {
                    Article updatedArticle = updateArticle(articleDTO, article);
                    articleRepository.merge(article);
                    return updatedArticle;
                })
                .orElseThrow(() -> new UpdateParameterServiceException("Can't update article because can't find it by id"));
        return conversionService.convert(updatedMergedArticle, ArticleDTO.class);
    }

    @Override
    @Transactional
    public List<ArticleDTO> findLatestFromEachSaleUser() {
        List<UserDTO> saleUsers = userService.findAllSales();
        return saleUsers.stream()
                .map(UserDTO::getId)
                .map(articleRepository::findLatestBySaleUserId)
                .map(article -> conversionService.convert(article, ArticleDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<ArticleDTO> findLatestBySaleUserId(UserDTOLogin userDTOLogin) {
        Long userId = userDTOLogin.getUserId();
        List<Article> articles = articleRepository.findLatestBySaleUserId(userId);
        return articles.stream()
                .map(article -> conversionService.convert(article, ArticleDTO.class))
                .collect(Collectors.toList());
    }

    private PageDTO<ArticleDTO> buildPageWithArticlesByUserId(Long currentPageNumber, Long amountOfPages, Long userId) {
        PageDTO<ArticleDTO> page = getPageWithArticles(amountOfPages);
        Long startNumberOnCurrentPage = paginationService.getStartEntityNumberOnCurrentPage(currentPageNumber, amountOfPages, AMOUNT_ON_ONE_PAGE);
        List<Article> articles = articleRepository.findOnPageByUserId(startNumberOnCurrentPage, AMOUNT_ON_ONE_PAGE, userId);
        addArticlesToPage(page, articles);
        return page;
    }

    private PageDTO<ArticleDTO> buildPageWithArticles(Long currentPageNumber, Long amountOfPages) {
        PageDTO<ArticleDTO> page = getPageWithArticles(amountOfPages);
        Long startNumberOnCurrentPage = paginationService.getStartEntityNumberOnCurrentPage(currentPageNumber, amountOfPages, AMOUNT_ON_ONE_PAGE);
        List<Article> articles = articleRepository.findOnPage(startNumberOnCurrentPage, AMOUNT_ON_ONE_PAGE);
        addArticlesToPage(page, articles);
        return page;
    }

    private PageDTO<ArticleDTO> getPageWithArticles(Long amountOfPages) {
        PageDTO<ArticleDTO> page = new PageDTO<>();
        page.setPagesAmount(amountOfPages);
        return page;
    }

    private void addArticlesToPage(PageDTO<ArticleDTO> page, List<Article> articles) {
        List<ArticleDTO> articlesDTO = getArticle(articles);
        page.getList().addAll(articlesDTO);
    }

    private List<ArticleDTO> getArticle(List<Article> articles) {
        return articles.stream()
                .map(article -> conversionService.convert(article, ArticleDTO.class))
                .collect(Collectors.toList());
    }

    private ArticleDTO addWithUser(ArticleDTO articleDTO) {
        try {
            Article article = conversionService.convert(articleDTO, Article.class);
            Optional.ofNullable(article)
                    .ifPresentOrElse(currentArticle -> articleRepository.persist(article), () -> {
                        throw new AddServiceException("Can't add new article on service level because user of articles " +
                                "doesn't exist");
                    });
            return conversionService.convert(article, ArticleDTO.class);
        } catch (PersistEntityRepositoryException e) {
            logger.error(e.getMessage(), e);
            throw new AddServiceException("Can't add new article on service level because its existing", e);
        }
    }

    private Article updateArticle(ArticleDTO articleDTO, Article article) {
        article.setId(articleDTO.getId());
        article.setName(articleDTO.getName());
        article.setShortDescription(articleDTO.getShortDescription());
        article.setFullDescription(articleDTO.getFullDescription());
        article.setDateAdded(LocalDate.now());
        return article;
    }
}
