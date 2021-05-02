package com.churilovich.bortnik.darya.shop.on.sofa.service;

import com.churilovich.bortnik.darya.shop.on.sofa.service.model.PageDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserDTOLogin;

public interface UserService {
    UserDTO getByUsername(String username);

    void add(UserDTO userDTO);

    void updateRole(UserDTO userDTO);

    void deleteById(Long id);

    void updatePassword(UserDTO userDTO);

    PageDTO<UserDTO> getUsersOnPage(Long currentPageNumber, UserDTOLogin userDTOLogin);
}
