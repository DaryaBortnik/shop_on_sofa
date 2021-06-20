package com.churilovich.bortnik.darya.shop.on.sofa.repository.impl;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.CategoryRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.ItemCategory;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryRepositoryImpl extends GenericRepositoryImpl<Long, ItemCategory> implements CategoryRepository {
}
