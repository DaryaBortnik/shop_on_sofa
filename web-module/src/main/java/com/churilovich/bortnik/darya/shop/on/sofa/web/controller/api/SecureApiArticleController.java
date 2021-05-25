package com.churilovich.bortnik.darya.shop.on.sofa.web.controller.api;

import com.churilovich.bortnik.darya.shop.on.sofa.service.ArticleService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.ValidationService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ArticleDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ResponseDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserDTOLogin;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class SecureApiArticleController {
    private final ArticleService articleService;
    private final ValidationService validationService;

    @GetMapping("/articles")
    public ResponseEntity<ResponseDTO<List<ArticleDTO>>> getArticles() {
        List<ArticleDTO> articles = articleService.findAll();
        return new ResponseEntity<>(new ResponseDTO<>(articles), HttpStatus.OK);
    }

    @GetMapping("/articles/{id}")
    public ResponseEntity<ResponseDTO<ArticleDTO>> getArticleById(@PathVariable Long id) {
        ArticleDTO article = articleService.findById(id);
        return new ResponseEntity<>(new ResponseDTO<>(article), HttpStatus.OK);
    }

    @PostMapping("/articles")
    public ResponseEntity<ResponseDTO> addArticle(@Valid @RequestBody ArticleDTO article,
                                                  @AuthenticationPrincipal UserDTOLogin userDTOLogin) {
        if (validationService.isUserHasFirstAndLastNames(userDTOLogin)) {
            ArticleDTO addedArticle = articleService.add(article, userDTOLogin);
            return new ResponseEntity<>(new ResponseDTO<>(addedArticle), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new ResponseDTO<>("failed to add new article because of the user has no " +
                    "first and last name"), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/articles/{id}")
    public ResponseEntity<ResponseDTO<String>> deleteArticleById(@PathVariable Long id) {
        Long deletedId = articleService.deleteById(id);
        return new ResponseEntity<>(new ResponseDTO<>("delete article with id = " + deletedId +
                " successfully"), HttpStatus.OK);
    }
}
