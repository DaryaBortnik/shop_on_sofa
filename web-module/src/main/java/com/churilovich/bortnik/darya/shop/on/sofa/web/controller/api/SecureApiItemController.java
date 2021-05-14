package com.churilovich.bortnik.darya.shop.on.sofa.web.controller.api;

import com.churilovich.bortnik.darya.shop.on.sofa.service.ItemService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.AddServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.DeleteByIdServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ArticleDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ItemDTO;
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
public class SecureApiItemController {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final ItemService itemService;

    @Autowired
    public SecureApiItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/items")
    public ResponseEntity<List<ItemDTO>> getItems() {
        List<ItemDTO> items = itemService.findAll();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/items/{id}")
    public ResponseEntity<ItemDTO> getItemById(@PathVariable Long id) {
        ItemDTO item = itemService.findById(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @PostMapping("/items")
    public ResponseEntity<String> addItem(@Valid @RequestBody ItemDTO item) {
        try {
            itemService.add(item);
            return new ResponseEntity<>("add successfully", HttpStatus.OK);
        } catch (AddServiceException e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>("add failed", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<String> deleteItemById(@PathVariable Long id) {
        try {
            itemService.deleteById(id);
            return new ResponseEntity<>("deleteById successfully", HttpStatus.OK);
        } catch (DeleteByIdServiceException e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>("deleteById failed", HttpStatus.NOT_FOUND);
        }
    }
}
