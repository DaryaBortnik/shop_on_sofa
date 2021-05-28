package com.churilovich.bortnik.darya.shop.on.sofa.service;

import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserDTOLogin;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserProfileDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.element.PageDTO;

import java.util.List;

public interface UserService {
    UserDTO getByUsername(String username);

    UserDTO add(UserDTO userDTO);

    void updateRole(UserDTO userDTO);

    void deleteById(Long id);

    void generateNewPassword(UserDTO userDTO);

    PageDTO<UserDTO> getUsersOnPage(Long currentPageNumber, UserDTOLogin userDTOLogin);

    UserDTO findById(Long id);

    UserProfileDTO updateUserProfileParameters(UserDTOLogin userDTOLogin, UserProfileDTO userProfileDTO);

    UserDTO getByFirstAndLastNames(String firstName, String lastName);

    void updateUserPassword(UserDTOLogin userDTOLogin, String oldPassword, String newPassword);

    List<UserDTO> findAllSales();

}
