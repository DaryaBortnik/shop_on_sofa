package com.churilovich.bortnik.darya.shop.on.sofa.repository.impl;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.ItemRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.Item;
import org.springframework.stereotype.Repository;

@Repository
public class ItemRepositoryImpl extends GenericRepositoryImpl<Long, Item> implements ItemRepository {
}
