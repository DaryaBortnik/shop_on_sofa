package com.churilovich.bortnik.darya.shop.on.sofa.service.impl;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.ItemRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.UserRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.Item;
import com.churilovich.bortnik.darya.shop.on.sofa.service.ItemCategoryService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.PaginationService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.GetByParameterServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ItemDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ItemCategoryService categoryService;
    @Mock
    private ConversionService conversionService;
    @Mock
    private PaginationService paginationService;
    @InjectMocks
    private ItemServiceImpl itemService;

    @Test
    public void shouldFindById() {
        Long id = 1L;
        Item item = new Item();
        item.setId(id);
        Optional<Item> optionalItem = Optional.of(item);
        when(itemRepository.findById(id)).thenReturn(optionalItem);
        ItemDTO expectedItemDTO = new ItemDTO();
        expectedItemDTO.setId(id);
        when(conversionService.convert(optionalItem.get(), ItemDTO.class)).thenReturn(expectedItemDTO);
        ItemDTO actualItem = itemService.findById(id);
        assertEquals(expectedItemDTO.getId(), actualItem.getId());
    }

    @Test
    public void shouldThrownExceptionIfCantFindById() {
        Long id = 1L;
        when(itemRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(GetByParameterServiceException.class, () -> itemService.findById(id));
    }

    @Test
    public void shouldFindAllItems() {
        List<Item> items = new ArrayList<>();
        Long id = 1L;
        Item item = new Item();
        item.setId(id);
        items.add(item);
        when(itemRepository.findAll()).thenReturn(items);
        List<ItemDTO> expectedItems = new ArrayList<>();
        ItemDTO expectedItemDTO = new ItemDTO();
        expectedItemDTO.setId(id);
        when(conversionService.convert(item, ItemDTO.class)).thenReturn(expectedItemDTO);
        expectedItems.add(expectedItemDTO);
        List<ItemDTO> actualItems = itemService.findAll();
        assertEquals(expectedItems.size(), actualItems.size());
    }

    @Test
    public void shouldReturnEmptyListIfCantFindItems() {
        List<ItemDTO> actualItems = itemService.findAll();
        assertTrue(actualItems.isEmpty());
    }

    @Test
    public void shouldDeleteById() {
        Long id = 1L;
        Item item = new Item();
        item.setId(id);
        Optional<Item> optionalItem = Optional.of(item);
        when(itemRepository.findById(id)).thenReturn(optionalItem);
        Item expectedItem = optionalItem.get();
        Long actualId = itemService.deleteById(id);
        assertEquals(expectedItem.getId(), actualId);
    }

    @Test
    public void shouldThrownExceptionIfCantFindArticleIdWhenDeletingItById() {
        Long id = 1L;
        when(itemRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(GetByParameterServiceException.class, () -> itemService.deleteById(id));
    }
}