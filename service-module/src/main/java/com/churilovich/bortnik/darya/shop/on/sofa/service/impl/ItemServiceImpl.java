package com.churilovich.bortnik.darya.shop.on.sofa.service.impl;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.ItemRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.exception.GetEntitiesAmountRepositoryException;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.Item;
import com.churilovich.bortnik.darya.shop.on.sofa.service.ItemService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.PaginationService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.GetArticlesServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.exception.GetByParameterServiceException;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ArticleDTO;
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
import java.util.stream.Collectors;

import static com.churilovich.bortnik.darya.shop.on.sofa.service.constants.PaginationValueConstants.AMOUNT_ON_ONE_PAGE;

@Service
public class ItemServiceImpl implements ItemService {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final ItemRepository itemRepository;
    private final ConversionService conversionService;
    private final PaginationService paginationService;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository,
                           ConversionService conversionService,
                           PaginationService paginationService) {
        this.itemRepository = itemRepository;
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
    public void add(ItemDTO itemDTO) {

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
            throw new GetArticlesServiceException("Can't get all reviews on current page on service level " +
                    "due to impossibility to get total amount of reviews", e);
        }
    }

    private Item getItem(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> {
                    throw new GetByParameterServiceException("Can't get item with id on service level : id = " + id);
                });
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
                .map(oneNews -> conversionService.convert(oneNews, ItemDTO.class))
                .collect(Collectors.toList());
    }
}
