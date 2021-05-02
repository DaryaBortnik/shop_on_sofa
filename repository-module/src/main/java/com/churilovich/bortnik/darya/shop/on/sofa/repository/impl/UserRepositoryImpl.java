package com.churilovich.bortnik.darya.shop.on.sofa.repository.impl;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.UserRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.exception.UserRepositoryRuntimeException;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.lang.invoke.MethodHandles;
import java.util.List;

import static com.churilovich.bortnik.darya.shop.on.sofa.repository.constants.QueryParameterConstants.EMAIL_QUERY_PARAMETER;

@Repository
public class UserRepositoryImpl extends GenericRepositoryImpl<Long, User> implements UserRepository {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public User getByUsername(String email) {
        logger.info("Getting user by email [{}] on repository level", email);
        String queryInStringFormat = "from " + entityClass.getName() + " where email =:email and is_deleted = 0";
        Query query = entityManager.createQuery(queryInStringFormat);
        query.setParameter(EMAIL_QUERY_PARAMETER, email);
        try {
            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            logger.error(e.getMessage(), e);
            throw new UserRepositoryRuntimeException("Can't get user by username on repository level : email = "
                    + email, e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> findAll(Long startNumberOnCurrentPage, Long amountOnOnePage) {
        logger.info("Finding all users on current page : start user number = [{}]", startNumberOnCurrentPage);
        String queryInStringFormat = "from " + entityClass.getName() + " where is_deleted = 0 order by email";
        Query query = entityManager.createQuery(queryInStringFormat);
        query.setMaxResults(Math.toIntExact(amountOnOnePage));
        query.setFirstResult(Math.toIntExact(startNumberOnCurrentPage));
        return query.getResultList();
    }
}
