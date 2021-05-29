package com.churilovich.bortnik.darya.shop.on.sofa.service.converter.shop;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.Shop;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.User;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ShopDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShopEntityToDTOConverter implements Converter<Shop, ShopDTO> {
    private final Converter<User, UserDTO> userConverter;

    @Override
    public ShopDTO convert(Shop shop) {
        ShopDTO shopDTO = new ShopDTO();
        shopDTO.setId(shop.getId());
        shopDTO.setName(shop.getName());
        shopDTO.setCertificate(shop.getCertificate());
        shopDTO.setIsDeleted(shop.getIsDeleted());
        shopDTO.setUserDTO(getConvertedUser(shop));
        return shopDTO;
    }

    private UserDTO getConvertedUser(Shop shop) {
        User user = shop.getUser();
        return userConverter.convert(user);
    }
}
