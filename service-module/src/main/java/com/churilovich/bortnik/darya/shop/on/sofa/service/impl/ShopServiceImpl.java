package com.churilovich.bortnik.darya.shop.on.sofa.service.impl;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.RoleRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.ShopRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.Role;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.Shop;
import com.churilovich.bortnik.darya.shop.on.sofa.service.ShopService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.GetByParameterServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ArticleDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ShopDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.churilovich.bortnik.darya.shop.on.sofa.repository.model.enums.RoleEnum.SALE_USER;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {
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
}
