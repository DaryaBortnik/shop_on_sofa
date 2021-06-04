package com.churilovich.bortnik.darya.shop.on.sofa.repository.impl;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.OrderRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.Item;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.Order;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.User;
import org.springframework.beans.factory.annotation.Qualifier;
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

    @Override
    @SuppressWarnings("unchecked")
    public List<Order> findAllByUserIdOnPage(Long startNumberOnCurrentPage, long amountOnOnePage, Long userId) {
        String queryInStringFormat = "from " + entityClass.getName() + " where item_id IN(select id from " + Item.class.getName() + " where user_id=:userId) order by dateAdded desc";
        Query query = entityManager.createQuery(queryInStringFormat);
        query.setParameter("userId", userId);
        query.setMaxResults(Math.toIntExact(amountOnOnePage));
        query.setFirstResult(Math.toIntExact(startNumberOnCurrentPage));
        return query.getResultList();
    }
}
