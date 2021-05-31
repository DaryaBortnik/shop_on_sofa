package com.churilovich.bortnik.darya.shop.on.sofa.service.impl;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.RoleRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.ShopRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.UserRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.exception.GetEntitiesAmountRepositoryException;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.Article;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.Role;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.Shop;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.User;
import com.churilovich.bortnik.darya.shop.on.sofa.service.PaginationService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.ShopService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.GetByParameterServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.GetOnPageServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.UpdateParameterServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ShopDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.element.PageDTO;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.stream.Collectors;

import static com.churilovich.bortnik.darya.shop.on.sofa.repository.model.enums.RoleEnum.SALE_USER;
import static com.churilovich.bortnik.darya.shop.on.sofa.service.constants.PaginationValueConstants.AMOUNT_ON_ONE_PAGE;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ShopRepository shopRepository;
    private final ConversionService conversionService;
    private final PaginationService<ShopRepository> paginationService;

    @Override
    public List<ShopDTO> findAllShops() {
        Role role = roleRepository.findByName(SALE_USER);
        Long roleId = role.getId();
        List<Shop> shops = shopRepository.findByRoleId(roleId);
        return shops.stream()
                .map(shop -> conversionService.convert(shop, ShopDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ShopDTO findById(Long id) {
        Shop shop = shopRepository.findById(id)
                .orElseThrow(() ->
                        new GetByParameterServiceException("Can't get shop with id on service level : id = " + id));
        return conversionService.convert(shop, ShopDTO.class);
    }

    @Override
    public ShopDTO findBySaleUserId(Long userId) {
        Shop shop = shopRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new GetByParameterServiceException("Can't get shop with id on service level : id = " + userId));
        return conversionService.convert(shop, ShopDTO.class);
    }

    @Override
    @Transactional
    public ShopDTO updateShopProfileParameters(Long userId, ShopDTO shopDTO) {
        Shop updatedShop = shopRepository.findByUserId(userId)
                .map(shop -> getUpdatedShop(shopDTO, shop))
                .orElseGet(() -> {
                    throw new UpdateParameterServiceException("Can't update user profile because user doesn't exist");
                });
        shopRepository.merge(updatedShop);
        return conversionService.convert(updatedShop, ShopDTO.class);
    }

    @Override
    @Transactional
    public void add(ShopDTO shop) {
        Shop newShop = new Shop();
        newShop.setName(shop.getName());
        newShop.setCertificate(shop.getCertificate());
        User user = userRepository.findById(shop.getUserDTO().getId())
                .orElseThrow();
        newShop.setUser(user);
        shopRepository.persist(newShop);
    }

    @Override
    public PageDTO<ShopDTO> getShopsOnPage(Long currentPageNumber) {
        try {
            Long amountOfPages = paginationService.getAmountOfPages(shopRepository);
            return buildPageWithShops(currentPageNumber, amountOfPages);
        } catch (GetEntitiesAmountRepositoryException e) {
            logger.error(e.getMessage(), e);
            throw new GetOnPageServiceException("Can't get all reviews on current page on service level " +
                    "due to impossibility to get total amount of reviews", e);
        }
    }

    private Shop getUpdatedShop(ShopDTO shopDTO, Shop shop) {
        shop.setName(shopDTO.getName());
        shop.setCertificate(shopDTO.getCertificate());
        return shop;
    }

    private PageDTO<ShopDTO> buildPageWithShops(Long currentPageNumber, Long amountOfPages) {
        PageDTO<ShopDTO> page = getPageWithShops(amountOfPages);
        Long startNumberOnCurrentPage = paginationService.getStartEntityNumberOnCurrentPage(currentPageNumber, amountOfPages, AMOUNT_ON_ONE_PAGE);
        List<Shop> shops = shopRepository.findOnPage(startNumberOnCurrentPage, AMOUNT_ON_ONE_PAGE);
        addShopsToPage(page, shops);
        return page;
    }

    private PageDTO<ShopDTO> getPageWithShops(Long amountOfPages) {
        PageDTO<ShopDTO> page = new PageDTO<>();
        if (amountOfPages == 0) {
            page.setPagesAmount(1L);
        } else {
            page.setPagesAmount(amountOfPages);
        }
        return page;
    }

    private void addShopsToPage(PageDTO<ShopDTO> page, List<Shop> shops) {
        List<ShopDTO> shopsDTO = getShops(shops);
        page.getList().addAll(shopsDTO);
    }

    private List<ShopDTO> getShops(List<Shop> shops) {
        return shops.stream()
                .map(shop -> conversionService.convert(shop, ShopDTO.class))
                .collect(Collectors.toList());
    }
}
