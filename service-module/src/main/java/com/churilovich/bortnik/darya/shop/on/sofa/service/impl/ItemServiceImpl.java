package com.churilovich.bortnik.darya.shop.on.sofa.service.impl;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.ItemRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.UserRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.exception.GetEntitiesAmountRepositoryException;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.Item;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.ItemCategory;
import com.churilovich.bortnik.darya.shop.on.sofa.service.ItemCategoryService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.ItemService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.PaginationService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.GetByParameterServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.GetOnPageServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ItemCategoryDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ItemDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.PageDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.churilovich.bortnik.darya.shop.on.sofa.service.constants.PaginationValueConstants.AMOUNT_ON_ONE_PAGE;

@Service
public class ItemServiceImpl implements ItemService {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final ItemCategoryService categoryService;
    private final ConversionService conversionService;
    private final PaginationService paginationService;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository,
                           UserRepository userRepository,
                           ItemCategoryService categoryService,
                           ConversionService conversionService,
                           PaginationService paginationService) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.categoryService = categoryService;
        this.conversionService = conversionService;
        this.paginationService = paginationService;
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
    @Transactional
    public ItemDTO add(ItemDTO itemDTO) {
        Long userId = itemDTO.getUserId();
        Item item = conversionService.convert(itemDTO, Item.class);
        item.setUniqueNumber(UUID.randomUUID().toString());
        userRepository.findById(userId).ifPresent(item::setUser);
        itemRepository.persist(item);
        return conversionService.convert(item, ItemDTO.class);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Item item = getItem(id);
        itemRepository.remove(item);
    }

    @Override
    @Transactional
    public PageDTO<ItemDTO> getItemsOnPage(Long currentPageNumber) {
        try {
            Long amountOfItems = itemRepository.getAmountOfEntities();
            Long amountOfPages = paginationService.getAmountOfPagesForElements(amountOfItems, AMOUNT_ON_ONE_PAGE);
            return buildPageWithItems(currentPageNumber, amountOfPages);
        } catch (GetEntitiesAmountRepositoryException e) {
            logger.error(e.getMessage(), e);
            throw new GetOnPageServiceException("Can't get all reviews on current page on service level " +
                    "due to impossibility to get total amount of reviews", e);
        }
    }

    @Override
    @Transactional
    public void updateItemDetails(ItemDTO itemDTO) {
        Long id = itemDTO.getId();
        Long categoryId = itemDTO.getItemCategoryDTO().getId();
        ItemCategoryDTO categoryDTO = categoryService.findById(categoryId);
        itemDTO.setItemCategoryDTO(categoryDTO);
        Item updatedItem = itemRepository.findById(id)
                .map(item -> updateItem(itemDTO, item))
                .get();
        itemRepository.merge(updatedItem);
    }

    @Override
    @Transactional
    public void copy(Long id) {
        Item item = getItem(id);
        Item copiedItem = new Item();
        copiedItem.setName(item.getName());
        copiedItem.setDescription(item.getDescription());
        copiedItem.setPrice(item.getPrice());
        copiedItem.setCategory(item.getCategory());
        copiedItem.setUser(item.getUser());
        copiedItem.setUniqueNumber(UUID.randomUUID().toString());
        itemRepository.persist(copiedItem);
    }

    private Item getItem(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() ->
                        new GetByParameterServiceException("Can't get item with id on service level : id = " + id));
    }

    private PageDTO<ItemDTO> buildPageWithItems(Long currentPageNumber, Long amountOfPages) {
        PageDTO<ItemDTO> page = new PageDTO<>();
        page.setPagesAmount(amountOfPages);
        currentPageNumber = paginationService.getCurrentPageNumber(currentPageNumber, amountOfPages);
        Long startNumberOnCurrentPage = paginationService.getElementPosition(currentPageNumber, AMOUNT_ON_ONE_PAGE);
        List<Item> items = itemRepository.findAllOnPage(startNumberOnCurrentPage, AMOUNT_ON_ONE_PAGE);
        List<ItemDTO> itemsDTO = getItemDTO(items);
        page.getList().addAll(itemsDTO);
        return page;
    }

    private List<ItemDTO> getItemDTO(List<Item> items) {
        return items.stream()
                .map(item -> conversionService.convert(item, ItemDTO.class))
                .collect(Collectors.toList());
    }

    private Item updateItem(ItemDTO itemDTO, Item item) {
        item.setName(itemDTO.getName());
        item.setDescription(itemDTO.getDescription());
        item.setPrice(itemDTO.getPrice());
        ItemCategoryDTO itemCategoryDTO = itemDTO.getItemCategoryDTO();
        item.setCategory(conversionService.convert(itemCategoryDTO, ItemCategory.class));
        return item;
    }
}
