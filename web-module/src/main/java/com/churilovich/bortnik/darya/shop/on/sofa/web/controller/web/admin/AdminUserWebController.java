package com.churilovich.bortnik.darya.shop.on.sofa.web.controller.web.admin;

import com.churilovich.bortnik.darya.shop.on.sofa.service.RoleService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.UserService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.RoleDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserDTOLogin;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserProfileDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.element.PageDTO;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminUserWebController {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final UserService userService;
    private final RoleService roleService;

    @GetMapping("/users")
    public String getAllUsers(@RequestParam(defaultValue = "1", value = "current_page") Long currentPageNumber,
                              UserDTO userDTO, @AuthenticationPrincipal UserDTOLogin userDTOLogin, Model model) {
        PageDTO<UserDTO> pageWithUsers = userService.getUsersOnPage(currentPageNumber, userDTOLogin);
        model.addAttribute("users", pageWithUsers.getList());
        model.addAttribute("page", pageWithUsers);
        List<RoleDTO> roles = roleService.findAll();
        model.addAttribute("roles", roles);
        userDTO.setRoleDTO(new RoleDTO());
        return "admin_get_all_users_page";
    }

    @GetMapping("/users/new")
    public String getAddNewUserPage(UserDTO userDTO, Model model) {
        List<RoleDTO> roles = roleService.findAll();
        model.addAttribute("roles", roles);
        userDTO.setRoleDTO(new RoleDTO());
        userDTO.setUserProfileDTO(new UserProfileDTO());
        if (!model.containsAttribute("userDTO")) {
            model.addAttribute("userDTO", userDTO);
        }
        return "admin_add_new_user_page";
    }

    @PostMapping("/users/new")
    public String addNewUser(@Valid UserDTO userDTO, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.getFlashAttributes().clear();
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userDTO", result);
            redirectAttributes.addFlashAttribute("userDTO", userDTO);
            return "redirect:/admin/users/new";
        } else {
            userService.add(userDTO);
            return "redirect:/admin/users";
        }
    }

    @PostMapping("/users/update/role")
    public String updateRole(UserDTO userDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "admin_get_all_users_page";
        } else {
            userService.updateRole(userDTO);
            return "redirect:/admin/users";
        }
    }

    @PostMapping("/users/delete")
    public String deleteUsers(@RequestParam("deleting_users_id") List<Long> ids) {
        ids.stream()
                .filter(Objects::nonNull)
                .forEach(userService::deleteById);
        return "redirect:/admin/users";
    }

    @PostMapping("/users/update/password")
    public String updatePassword(UserDTO userDTO, BindingResult result) {

        if (result.hasErrors()) {
            return "admin_get_all_users_page";
        } else {
            userService.generateNewPassword(userDTO);
            return "redirect:/admin/users";
        }
    }
}
