package com.churilovich.bortnik.darya.shop.on.sofa.web.controller.web;

import com.churilovich.bortnik.darya.shop.on.sofa.service.RoleService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.UserService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.AddServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.RoleDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserProfileDTO;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

@Controller
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationWebController {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final RoleService roleService;
    private final UserService userService;

    @GetMapping
    public String getChoiceUserRolePage(Model model) {
        List<RoleDTO> roles = roleService.findForRegistration();
        model.addAttribute("roles", roles);
        return "common_choose_role_on_registration_page";
    }

    @GetMapping("/new")
    public String getAddNewUserPage(@RequestParam(required = false, name = "chosen_role") Long id, UserDTO userDTO, Model model) {
        RoleDTO role = roleService.findById(id);
        userDTO.setRoleDTO(role);
        userDTO.setUserProfileDTO(new UserProfileDTO());
        model.addAttribute("userDTO", userDTO);
        return "common_registration_page";
    }

    @PostMapping("/new")
    public String addNewUser(@RequestParam(required = false, name = "role_id") Long roleId,
                             @Valid UserDTO userDTO, BindingResult result, RedirectAttributes redirectAttributes) {
        try {
            if (result.hasErrors()) {
                redirectAttributes.getFlashAttributes().clear();
                redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userDTO", result);
                redirectAttributes.addFlashAttribute("userDTO", userDTO);
                return "redirect:/registration/new";
            } else {
                RoleDTO role = roleService.findById(roleId);
                userDTO.setRoleDTO(role);
                userService.add(userDTO);
                return "redirect:/login";
            }
        } catch (AddServiceException e) {
            logger.error(e.getMessage(), e);
            return "error_page";
        }
    }
}
