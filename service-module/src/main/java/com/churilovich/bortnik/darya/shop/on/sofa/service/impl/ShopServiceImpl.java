package com.churilovich.bortnik.darya.shop.on.sofa.service.impl;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.RoleRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.ShopRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.UserRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.Role;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.Shop;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.User;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.UserProfile;
import com.churilovich.bortnik.darya.shop.on.sofa.service.ShopService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.GetByParameterServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.UpdateParameterServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ShopDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserProfileDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.churilovich.bortnik.darya.shop.on.sofa.repository.model.enums.RoleEnum.SALE_USER;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ShopRepository shopRepository;
    private final ConversionService conversionService;

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

    private Shop getUpdatedShop(ShopDTO shopDTO, Shop shop) {
        shop.setName(shopDTO.getName());
        shop.setCertificate(shopDTO.getCertificate());
        return shop;
    }
}
