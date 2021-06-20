package com.churilovich.bortnik.darya.shop.on.sofa.web.controller.api;

import com.churilovich.bortnik.darya.shop.on.sofa.service.ItemService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ItemDTO;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.RequiredArgsConstructor;
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
import java.util.List;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class SecureApiItemController {
    private final ItemService itemService;

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
    public ResponseEntity<ItemDTO> addItem(@Valid @RequestBody ItemDTO item) {
        ItemDTO addedItem = itemService.add(item);
        return new ResponseEntity<>(addedItem, HttpStatus.CREATED);
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<JSONPObject> deleteItemById(@PathVariable Long id) {
        Long deletedId = itemService.deleteById(id);
        JSONPObject result = new JSONPObject("message", "delete item with id = " + deletedId + " successfully");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
