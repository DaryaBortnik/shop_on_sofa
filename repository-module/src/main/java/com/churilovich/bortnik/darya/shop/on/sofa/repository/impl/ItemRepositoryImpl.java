package com.churilovich.bortnik.darya.shop.on.sofa.repository.impl;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.ItemRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.Item;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class ItemRepositoryImpl extends GenericRepositoryImpl<Long, Item> implements ItemRepository {
    @Override
    @SuppressWarnings("unchecked")
    public List<Item> findAllOnPage(Long startNumberOnCurrentPage, long amountOnOnePage) {
        String queryInStringFormat = "from " + entityClass.getName() + " order by name";
        Query query = entityManager.createQuery(queryInStringFormat);
        query.setMaxResults(Math.toIntExact(amountOnOnePage));
        query.setFirstResult(Math.toIntExact(startNumberOnCurrentPage));
        return query.getResultList();
    }
}
