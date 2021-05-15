package com.churilovich.bortnik.darya.shop.on.sofa.web.controller.api;

import com.churilovich.bortnik.darya.shop.on.sofa.service.ArticleService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.AddServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.DeleteByIdServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ArticleDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController
@RequestMapping("api/v1")
public class SecureApiArticleController {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final ArticleService articleService;

    @Autowired
    public SecureApiArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/articles")
    public ResponseEntity<List<ArticleDTO>> getArticles() {
        List<ArticleDTO> articles = articleService.findAll();
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @GetMapping("/articles/{id}")
    public ResponseEntity<ArticleDTO> getArticleById(@PathVariable Long id) {
        ArticleDTO article = articleService.findById(id);
        return new ResponseEntity<>(article, HttpStatus.OK);
    }

    @PostMapping("/articles")
    public ResponseEntity<String> addArticle(@Valid @RequestBody ArticleDTO article) {
        try {
            articleService.add(article);
            return new ResponseEntity<>("add successfully", HttpStatus.OK);
        } catch (AddServiceException e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>("add failed", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/articles/{id}")
    public ResponseEntity<String> deleteArticleById(@PathVariable Long id) {
        try {
            articleService.deleteById(id);
            return new ResponseEntity<>("deleteById successfully", HttpStatus.OK);
        } catch (DeleteByIdServiceException e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>("deleteById failed", HttpStatus.NOT_FOUND);
        }
    }
}
