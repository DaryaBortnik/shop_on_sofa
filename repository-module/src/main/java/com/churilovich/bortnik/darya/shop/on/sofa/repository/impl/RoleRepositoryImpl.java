package com.churilovich.bortnik.darya.shop.on.sofa.repository.impl;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.RoleRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.exception.GetRoleByNameRepositoryException;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.Role;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.enums.RoleEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.lang.invoke.MethodHandles;
import java.util.List;

@Repository
public class RoleRepositoryImpl extends GenericRepositoryImpl<Long, Role> implements RoleRepository {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public Role findByName(RoleEnum name) {
        String queryInStringFormat = "from " + entityClass.getName() + " where name =:name";
        Query query = entityManager.createQuery(queryInStringFormat);
        query.setParameter("name", name);
        try {
            return (Role) query.getSingleResult();
        } catch (NoResultException e) {
            logger.error(e.getMessage(), e);
            throw new GetRoleByNameRepositoryException("Can't get role by name on repository level : name = "
                    + name, e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Role> findForRegistration() {
        String queryInStringFormat = "from " + entityClass.getName() + " where name not in ('ADMINISTRATOR','SECURE_API_USER')";
        Query query = entityManager.createQuery(queryInStringFormat);
        return query.getResultList();
    }
}
