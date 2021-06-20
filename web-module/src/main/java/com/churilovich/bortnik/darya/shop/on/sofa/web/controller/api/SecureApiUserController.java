package com.churilovich.bortnik.darya.shop.on.sofa.web.controller.api;

import com.churilovich.bortnik.darya.shop.on.sofa.service.UserService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.element.ResponseDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class SecureApiUserController {
    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<ResponseDTO<List<UserDTO>>> getArticles() {
        List<UserDTO> usersDTO = userService.findAll();
        return new ResponseEntity<>(new ResponseDTO<>(usersDTO), HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<UserDTO> addUser(@Valid @RequestBody UserDTO userDTO) {
        UserDTO addedUser = userService.add(userDTO);
        return new ResponseEntity<>(addedUser, HttpStatus.CREATED);
    }
}


