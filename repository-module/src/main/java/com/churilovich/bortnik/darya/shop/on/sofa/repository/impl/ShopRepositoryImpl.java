package com.churilovich.bortnik.darya.shop.on.sofa.repository.impl;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.ShopRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.Shop;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;

@Repository
public class ShopRepositoryImpl extends GenericRepositoryImpl<Long, Shop> implements ShopRepository {

    @Override
    @SuppressWarnings("unchecked")
    public List<Shop> findByRoleId(Long roleId) {
        String queryInStringFormat = "from " + entityClass.getName() + " where user_id IN(select id from " + User.class.getName() + " where role_id=:roleId)";
        Query query = entityManager.createQuery(queryInStringFormat);
        query.setParameter("roleId", roleId);
        return query.getResultList();
    }

    @Override
    public Optional<Shop> findByUserId(Long userId) {
        try {
            String queryInStringFormat = "from " + entityClass.getName() + " where user_id=:userId";
            Query query = entityManager.createQuery(queryInStringFormat);
            query.setParameter("userId", userId);
            return Optional.ofNullable((Shop) query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Shop> findOnPage(Long startNumberOnCurrentPage, long amountOnOnePage) {
        String queryInStringFormat = "from " + entityClass.getName();
        Query query = entityManager.createQuery(queryInStringFormat);
        query.setMaxResults(Math.toIntExact(amountOnOnePage));
        query.setFirstResult(Math.toIntExact(startNumberOnCurrentPage));
        return query.getResultList();
    }
}
