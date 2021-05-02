package com.churilovich.bortnik.darya.shop.on.sofa.web.controller;

import com.churilovich.bortnik.darya.shop.on.sofa.service.RoleService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.UserService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.UserServiceRuntimeException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.PageDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.RoleDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserDTOLogin;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserInformationDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/admin")
public class UserWebController {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserWebController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public String getAllUsers(@RequestParam(defaultValue = "1", value = "current_page") Long currentPageNumber,
                              UserDTO userDTO, @AuthenticationPrincipal UserDTOLogin userDTOLogin, Model model) {
        logger.info("Getting all users on web controller level");
        try {
            PageDTO<UserDTO> pageWithUsers = userService.getUsersOnPage(currentPageNumber, userDTOLogin);
            model.addAttribute("users", pageWithUsers.getList());
            model.addAttribute("page", pageWithUsers);
            List<RoleDTO> roles = roleService.findAll();
            model.addAttribute("roles", roles);
            userDTO.setRoleDTO(new RoleDTO());
            return "get_all_users_page";
        } catch (UserServiceRuntimeException e) {
            logger.error(e.getMessage(), e);
            return "error_page";
        }
    }

    @GetMapping("/users/new")
    public String getNewUserPage(UserDTO userDTO, Model model) {
        logger.info("Getting new user page on web controller level");
        List<RoleDTO> roles = roleService.findAll();
        model.addAttribute("roles", roles);
        userDTO.setRoleDTO(new RoleDTO());
        userDTO.setUserInformationDTO(new UserInformationDTO());
        return "add_new_user_page";
    }

    @PostMapping("/users/new")
    public String addNewUser(@Valid UserDTO userDTO, BindingResult result) {
        logger.info("Adding new user on web controller level");
        try {
            if (result.hasErrors()) {
                return "add_new_user_page";
            } else {
                userService.add(userDTO);
                return "redirect:/admin/users";
            }
        } catch (UserServiceRuntimeException e) {
            logger.error(e.getMessage(), e);
            return "error_page";
        }
    }

    @PostMapping("/users/update/role")
    public String updateRole(UserDTO userDTO, BindingResult result) {
        logger.info("Updating role on web controller level");
        try {
            if (result.hasErrors()) {
                return "get_all_users_page";
            } else {
                userService.updateRole(userDTO);
                return "redirect:/admin/users";
            }
        } catch (UserServiceRuntimeException e) {
            logger.error(e.getMessage(), e);
            return "error_page";
        }
    }

    @PostMapping("/users/delete")
    public String deleteUsers(@RequestParam("deleting_users_id") List<Long> ids) {
        try {
            ids.stream()
                    .filter(Objects::nonNull)
                    .forEach(userService::deleteById);
            return "redirect:/admin/users";
        } catch (UserServiceRuntimeException e) {
            logger.error(e.getMessage(), e);
            return "error_page";
        }
    }

    @PostMapping("/users/update/password")
    public String updatePassword(UserDTO userDTO, BindingResult result) {
        logger.info("Updating password on web controller level");
        try {
            if (result.hasErrors()) {
                return "get_all_users_page";
            } else {
                userService.updatePassword(userDTO);
                return "redirect:/admin/users";
            }
        } catch (UserServiceRuntimeException e) {
            logger.error(e.getMessage(), e);
            return "error_page";
        }
    }
}
