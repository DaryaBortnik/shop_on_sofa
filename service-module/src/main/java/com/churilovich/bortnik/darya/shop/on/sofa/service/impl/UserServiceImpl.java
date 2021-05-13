package com.churilovich.bortnik.darya.shop.on.sofa.service.impl;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.RoleRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.UserProfileRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.UserRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.exception.GetEntitiesAmountRepositoryException;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.exception.GetUserByUsernameRepositoryException;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.exception.PersistEntityRepositoryException;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.Role;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.User;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.UserProfile;
import com.churilovich.bortnik.darya.shop.on.sofa.service.GenerationPasswordService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.MailService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.PaginationService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.UserService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.AddServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.DeleteByIdServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.GetByParameterServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.GetUsersOnPageServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.UpdatePasswordServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.UpdateRoleServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.PageDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserDTOLogin;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserProfileDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.churilovich.bortnik.darya.shop.on.sofa.service.constants.PaginationValueConstants.AMOUNT_ON_ONE_PAGE;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserProfileRepository userProfileRepository;
    private final ConversionService conversionService;
    private final GenerationPasswordService generationPasswordService;
    private final MailService mailService;
    private final PaginationService paginationService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           UserProfileRepository userProfileRepository,
                           ConversionService conversionService,
                           GenerationPasswordService generationPasswordService,
                           MailService mailService,
                           PaginationService paginationService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userProfileRepository = userProfileRepository;
        this.conversionService = conversionService;
        this.generationPasswordService = generationPasswordService;
        this.mailService = mailService;
        this.paginationService = paginationService;
    }

    @Override
    @Transactional
    public UserDTO getByUsername(String username) {
        try {
            User user = userRepository.getByUsername(username);
            return conversionService.convert(user, UserDTO.class);
        } catch (GetUserByUsernameRepositoryException e) {
            logger.error(e.getMessage(), e);
            throw new GetByParameterServiceException("Can't get user by username on service level : username = "
                    + username, e);
        }
    }

    @Override
    @Transactional
    public void add(UserDTO userDTO) {
        try {
            User user = conversionService.convert(userDTO, User.class);
            Optional.ofNullable(user)
                    .ifPresentOrElse(this::addUser, () -> {
                        throw new AddServiceException("Can't add new user on service level because it's null");
                    });
        } catch (PersistEntityRepositoryException e) {
            logger.error(e.getMessage(), e);
            throw new AddServiceException("Can't add new user on service level because its existing", e);
        }
    }

    @Override
    @Transactional
    public void updateRole(UserDTO userDTO) {
        Role role = getRoleById(userDTO);
        userRepository.findById(userDTO.getId())
                .ifPresentOrElse(user -> updateRole(user, role), () -> {
                    throw new UpdateRoleServiceException("Can't update user role on service level : user = " + userDTO);
                });
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        userRepository.findById(id)
                .ifPresentOrElse(userRepository::remove, () -> {
                    throw new DeleteByIdServiceException("Can't deleteById user by id on service level because can't found user : " +
                            "id = " + id);
                });
    }

    @Override
    public void generateNewPassword(UserDTO userDTO) {
        userRepository.findById(userDTO.getId())
                .ifPresentOrElse(this::giveUserNewPassword, () -> {
                    throw new UpdatePasswordServiceException("Can't update user password on service level : user = "
                            + userDTO);
                });
    }

    @Override
    @Transactional
    public PageDTO<UserDTO> getUsersOnPage(Long currentPageNumber, UserDTOLogin userDTOLogin) {
        try {
            Long amountOfUsers = userRepository.getAmountOfEntities();
            Long amountOfPages = paginationService.getAmountOfPagesForElements(amountOfUsers, AMOUNT_ON_ONE_PAGE);
            return buildPageWithUsers(currentPageNumber, userDTOLogin, amountOfPages);
        } catch (GetEntitiesAmountRepositoryException e) {
            logger.error(e.getMessage(), e);
            throw new GetUsersOnPageServiceException("Can't get all users on current page on service level " +
                    "due to impossibility to get total amount of users", e);
        }
    }

    @Override
    @Transactional
    public UserDTO findById(Long id) {
        User user = getUser(id);
        return conversionService.convert(user, UserDTO.class);
    }

    @Override
    @Transactional
    public void updateUserProfileParameters(UserDTOLogin userDTOLogin, UserProfileDTO userProfileDTO) {
        Long id = userDTOLogin.getUserId();
        UserProfile userProfileUpdate = userProfileRepository.findById(id)
                .map(userProfile -> updateProfile(userProfileDTO, userProfile))
                .orElseGet(() -> getUpdatedUserProfile(userProfileDTO));
        userProfileRepository.merge(userProfileUpdate);
    }

    @Override
    @Transactional
    public UserDTO getByFirstAndLastNames(String firstName, String lastName) {
        UserProfile userProfile = userProfileRepository.findByFirstAndLastNames(firstName, lastName);
        Long id = userProfile.getId();
        return findById(id);
    }

    @Override
    @Transactional
    public void updateUserPassword(UserDTOLogin userDTOLog, String oldPassword, String newPassword) {
        Long id = userDTOLog.getUserId();
        userRepository.findById(id)
                .ifPresentOrElse(user -> generatePassword(user, oldPassword, newPassword), () -> {
                    throw new UpdatePasswordServiceException("Can't update password, because can't find user with this id");
                });
    }

    private void addUser(User user) {
        buildUser(user);
        userRepository.persist(user);
    }

    private void buildUser(User user) {
        Long roleId = user.getRole().getId();
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> {
                    throw new GetByParameterServiceException("Can't get role with id : id = " + roleId);
                });
        user.setRole(role);
        user.getUserProfile().setUser(user);
        user.setPassword(generateNewPassword());
        user.setIsDeleted(false);
    }

    private String generateNewPassword() {
        String password = generationPasswordService.generate();
        return generationPasswordService.encode(password);
    }

    private Role getRoleById(UserDTO userDTO) {
        return roleRepository.findById(userDTO.getRoleDTO().getId())
                .orElse(new Role());
    }

    private void updateRole(User user, Role role) {
        user.setRole(role);
        userRepository.merge(user);
    }

    private void giveUserNewPassword(User user) {
        String password = generatePassword(user);
        mailService.sendMessageToEmail(user.getEmail(), password);
    }

    @Transactional
    String generatePassword(User user) {
        String newPassword = generateNewPassword();
        user.setPassword(newPassword);
        userRepository.merge(user);
        return newPassword;
    }

    private PageDTO<UserDTO> buildPageWithUsers(Long currentPageNumber, UserDTOLogin userDTOLogin, Long amountOfPages) {
        PageDTO<UserDTO> page = new PageDTO<>();
        page.setPagesAmount(amountOfPages);
        currentPageNumber = paginationService.getCurrentPageNumber(currentPageNumber, amountOfPages);
        Long startNumberOnCurrentPage = paginationService.getElementPosition(currentPageNumber, AMOUNT_ON_ONE_PAGE);
        List<User> users = userRepository.findAll(startNumberOnCurrentPage, AMOUNT_ON_ONE_PAGE);
        List<UserDTO> usersDTO = getUsersDTO(userDTOLogin, users);
        page.setList(usersDTO);
        return page;
    }

    private List<UserDTO> getUsersDTO(UserDTOLogin userDTOLogin, List<User> users) {
        return users.stream()
                .map(user -> conversionService.convert(user, UserDTO.class))
                .filter(Objects::nonNull)
                .filter(user -> !user.getId().equals(userDTOLogin.getUserId()))
                .collect(Collectors.toList());
    }

    private User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    throw new GetByParameterServiceException("Can't get user with id on service level : id = " + id);
                });
    }

    private UserProfile updateProfile(UserProfileDTO userProfileDTO, UserProfile userProfile) {
        userProfile.setPhoneNumber(userProfileDTO.getPhoneNumber());
        userProfile.setAddress(userProfileDTO.getAddress());
        userProfile.setFirstName(userProfileDTO.getFirstName());
        userProfile.setLastName(userProfileDTO.getLastName());
        userProfile.setMiddleName(userProfileDTO.getMiddleName());
        return userProfile;
    }


    private UserProfile getUpdatedUserProfile(UserProfileDTO userProfileDTO) {
        User user = getUser(userProfileDTO.getId());
        UserProfile userProfile = new UserProfile(userProfileDTO.getId(), user);
        return updateProfile(userProfileDTO, userProfile);
    }

    private void generatePassword(User user, String oldPassword, String newPassword) {
        String password = user.getPassword();
        if (generationPasswordService.matches(oldPassword, password)) {
            String newEncodedPassword = generationPasswordService.encode(newPassword);
            user.setPassword(newEncodedPassword);
            userRepository.merge(user);
        } else {
            throw new UpdatePasswordServiceException("Can't update password, because old password is not mapping with password from database");
        }
    }
}
