package com.churilovich.bortnik.darya.shop.on.sofa.repository.impl;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.OrderRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class OrderRepositoryImpl extends GenericRepositoryImpl<Long, Order> implements OrderRepository {
    @Override
    @SuppressWarnings("unchecked")
    public List<Order> findAllOnPage(Long startNumberOnCurrentPage, long amountOnOnePage) {
        String queryInStringFormat = "from " + entityClass.getName() + " order by dateAdded";
        Query query = entityManager.createQuery(queryInStringFormat);
        query.setMaxResults(Math.toIntExact(amountOnOnePage));
        query.setFirstResult(Math.toIntExact(startNumberOnCurrentPage));
        return query.getResultList();
    }
}
