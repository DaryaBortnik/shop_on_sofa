package com.churilovich.bortnik.darya.shop.on.sofa.web.controller.web.customer;

import com.churilovich.bortnik.darya.shop.on.sofa.service.UserProfileService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.UserService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserDTOLogin;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserProfileDTO;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user/customer")
public class CustomerProfileWebController {
    private final UserService userService;
    private final UserProfileService profileService;

    public CustomerProfileWebController(UserService userService, UserProfileService profileService) {
        this.userService = userService;
        this.profileService = profileService;
    }

    @GetMapping("/profile")
    public String getUserProfilePage(@AuthenticationPrincipal UserDTOLogin userDTOLogin, Model model) {
        UserProfileDTO userProfileDTO = profileService.getUserProfile(userDTOLogin);
        model.addAttribute("userProfileDTO", userProfileDTO);
        return "get_user_profile_page";
    }

    @GetMapping("/profile/update/parameters")
    public String getUpdateUserProfilePage(@AuthenticationPrincipal UserDTOLogin userDTOLogin, Model model) {
        UserProfileDTO userProfile = profileService.getUserProfile(userDTOLogin);
        model.addAttribute("userProfileDTO", userProfile);
        String newPassword = Strings.EMPTY;
        model.addAttribute("password", newPassword);
        return "get_update_user_profile_page";
    }

    @PostMapping("/profile/update/parameters")
    public String updateParameters(@AuthenticationPrincipal UserDTOLogin userDTOLogin, UserProfileDTO userProfileDTO,
                                   BindingResult result) {
        if (result.hasErrors()) {
            return "get_update_user_profile_page";
        } else {
            userService.updateUserProfileParameters(userDTOLogin, userProfileDTO);
            return "redirect:/user/customer/profile";
        }
    }

    @PostMapping("/profile/update/password")
    public String updatePassword(@AuthenticationPrincipal UserDTOLogin userDTOLogin, @RequestParam("old_password") String oldPassword,
                                 @RequestParam("new_password") String newPassword, BindingResult result) {
        if (result.hasErrors()) {
            return "get_update_user_profile_page";
        } else {
            userService.updateUserPassword(userDTOLogin, oldPassword, newPassword);
            return "redirect:/user/customer/profile";
        }
    }
}

