package com.churilovich.bortnik.darya.shop.on.sofa.web.controller.api;

import com.churilovich.bortnik.darya.shop.on.sofa.service.UserService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class SecureApiUserController {
    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<UserDTO> addUser(@Valid @RequestBody UserDTO userDTO) {
        UserDTO addedUser = userService.add(userDTO);
        return new ResponseEntity<>(addedUser, HttpStatus.CREATED);
    }
}


