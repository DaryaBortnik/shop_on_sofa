package com.churilovich.bortnik.darya.shop.on.sofa.service.impl;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.ItemRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.UserRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.Item;
import com.churilovich.bortnik.darya.shop.on.sofa.service.ItemService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.UserService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.GetByParameterServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ItemDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final ConversionService conversionService;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository,
                           ConversionService conversionService) {
        this.itemRepository = itemRepository;
        this.conversionService = conversionService;
    }

    @Override
    public List<ItemDTO> findAll() {
        return itemRepository.findAll().stream()
                .map(item -> conversionService.convert(item, ItemDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ItemDTO findById(Long id) {
        Item item = getItem(id);
        return conversionService.convert(item, ItemDTO.class);
    }

    @Override
    public void add(ItemDTO itemDTO) {

    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Item item = getItem(id);
        itemRepository.remove(item);
    }

    private Item getItem(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> {
                    throw new GetByParameterServiceException("Can't get item with id on service level : id = " + id);
                });
    }
}
