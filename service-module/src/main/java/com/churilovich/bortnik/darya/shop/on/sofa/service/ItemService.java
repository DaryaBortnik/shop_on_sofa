package com.churilovich.bortnik.darya.shop.on.sofa.service;

import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ItemDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ShopDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserDTOLogin;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.element.PageDTO;

import java.util.List;

public interface ItemService {
    List<ItemDTO> findAll();

    ItemDTO findById(Long id);

    ItemDTO add(ItemDTO itemDTO);

    Long deleteById(Long id);

    PageDTO<ItemDTO> getAllOnPage(Long currentPageNumber);

    void updateItemDetails(ItemDTO item);

    void copy(Long id);

    List<ItemDTO> findByCategoryId(Long id);

    PageDTO<ItemDTO> getBySaleUserIdOnPage(Long currentPageNumber, UserDTOLogin userDTOLogin);

    List<ItemDTO> findInShop(ShopDTO shop);
}
