package com.churilovich.bortnik.darya.shop.on.sofa.repository.impl;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.ItemRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.element.Report;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.Item;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class ItemRepositoryImpl extends GenericRepositoryImpl<Long, Item> implements ItemRepository {
    @Override
    @SuppressWarnings("unchecked")
    public List<Item> findAllOnPage(Long startNumberOnCurrentPage, long amountOnOnePage) {
        String queryInStringFormat = "from " + entityClass.getName() + " order by name, price";
        Query query = entityManager.createQuery(queryInStringFormat);
        query.setMaxResults(Math.toIntExact(amountOnOnePage));
        query.setFirstResult(Math.toIntExact(startNumberOnCurrentPage));
        return query.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Report> findAllReportGroupByNameAndPrice() {
        Query getReportQuery = entityManager.createNamedQuery("getReport");
        return getReportQuery.getResultList();
    }
}
