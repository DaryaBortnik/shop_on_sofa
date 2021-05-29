package com.churilovich.bortnik.darya.shop.on.sofa.repository.impl;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.ShopRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.Shop;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class ShopRepositoryImpl extends GenericRepositoryImpl<Long, Shop> implements ShopRepository {

    @Override
    @SuppressWarnings("unchecked")
    public List<Shop> findByRoleId(Long roleId) {
        String queryInStringFormat = "from " + entityClass.getName() + " WHERE user_id IN(select id from " + User.class.getName() + " where role_id=:roleId)";
        Query query = entityManager.createQuery(queryInStringFormat);
        query.setParameter("roleId", roleId);
        return query.getResultList();
    }
}
