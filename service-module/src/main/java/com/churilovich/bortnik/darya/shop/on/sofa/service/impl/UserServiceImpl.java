package com.churilovich.bortnik.darya.shop.on.sofa.service.impl;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.RoleRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.UserRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.exception.GetEntitiesAmountRepositoryException;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.exception.GetUserByUsernameRepositoryException;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.exception.PersistEntityRepositoryException;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.Role;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.User;
import com.churilovich.bortnik.darya.shop.on.sofa.service.GenerationPasswordService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.MailService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.PaginationService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.UserService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.AddUserServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.DeleteByIdServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.GetUserByUsernameServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.GetUsersOnPageServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.NullServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.UpdatePasswordServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.UpdateRoleServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.PageDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserDTOLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.churilovich.bortnik.darya.shop.on.sofa.service.constants.PaginationValueConstants.AMOUNT_ON_ONE_PAGE;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ConversionService conversionService;
    private final GenerationPasswordService generationPasswordService;
    private final MailService mailService;
    private final PaginationService paginationService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           ConversionService conversionService,
                           GenerationPasswordService generationPasswordService,
                           MailService mailService,
                           PaginationService paginationService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.conversionService = conversionService;
        this.generationPasswordService = generationPasswordService;
        this.mailService = mailService;
        this.paginationService = paginationService;
    }

    @Override
    @Transactional
    public UserDTO getByUsername(String username) {
        logger.info("Getting user by username [{}] on service level ", username);
        try {
            User user = userRepository.getByUsername(username);
            return conversionService.convert(user, UserDTO.class);
        } catch (GetUserByUsernameRepositoryException e) {
            logger.error(e.getMessage(), e);
            throw new GetUserByUsernameServiceException("Can't get user by username on service level : username = "
                    + username, e);
        }
    }

    @Override
    @Transactional
    public void add(UserDTO userDTO) {
        logger.info("Adding new user [{}] on service level", userDTO);
        try {
            User user = conversionService.convert(userDTO, User.class);
            if (Objects.nonNull(user)) {
                user.getUserInformation().setUser(user);
                String password = generationPasswordService.generate();
                String encodedPassword = generationPasswordService.encode(password);
                user.setPassword(encodedPassword);
                user.setIsDeleted(false);
                userRepository.persist(user);
            } else {
                throw new NullServiceException("Can't add new user on service level because it's null");
            }
        } catch (PersistEntityRepositoryException e) {
            logger.error(e.getMessage(), e);
            throw new AddUserServiceException("Can't add new user on service level because its existing", e);
        }
    }

    @Override
    @Transactional
    public void updateRole(UserDTO userDTO) {
        logger.info("Updating role to [{}] on service level", userDTO.getRoleDTO());
        Role role = getRoleById(userDTO);
        userRepository.findById(userDTO.getId())
                .ifPresentOrElse(user -> updateRole(user, role), () -> {
                    throw new UpdateRoleServiceException("Can't update user role on service level : user = " + userDTO);
                });
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        logger.info("Deleting user by id [{}] on service level", id);
        userRepository.findById(id)
                .ifPresentOrElse(userRepository::remove, () -> {
                    throw new DeleteByIdServiceException("Can't delete user by id on service level because can't found user : " +
                            "id = " + id);
                });
    }

    @Override
    @Transactional
    public void updatePassword(UserDTO userDTO) {
        logger.info("Updating password on random way on service level");
        userRepository.findById(userDTO.getId())
                .ifPresentOrElse(this::updatePassword, () -> {
                    throw new UpdatePasswordServiceException("Can't update user password on service level : user = "
                            + userDTO);
                });
    }

    @Override
    @Transactional
    public PageDTO<UserDTO> getUsersOnPage(Long currentPageNumber, UserDTOLogin userDTOLogin) {
        logger.info("Getting all reviews on current page [{}] on service level", currentPageNumber);
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

    private Role getRoleById(UserDTO userDTO) {
        return roleRepository.findById(userDTO.getRoleDTO().getId())
                .orElse(new Role());
    }

    private void updateRole(User user, Role role) {
        user.setRole(role);
        userRepository.merge(user);
    }

    private void updatePassword(User user) {
        String password = generationPasswordService.generate();
        mailService.sendMessageToEmail(user.getEmail(), password);
        String encodedPassword = generationPasswordService.encode(password);
        user.setPassword(encodedPassword);
        userRepository.merge(user);
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
}
