package com.churilovich.bortnik.darya.shop.on.sofa.repository.impl;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.UserProfileRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.entity.UserProfile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.lang.invoke.MethodHandles;
import java.util.Optional;

import static com.churilovich.bortnik.darya.shop.on.sofa.repository.constants.QueryParameterConstants.FIRST_NAME_QUERY_PARAMETER;
import static com.churilovich.bortnik.darya.shop.on.sofa.repository.constants.QueryParameterConstants.LAST_NAME_QUERY_PARAMETER;

@Repository
public class UserProfileRepositoryImpl extends GenericRepositoryImpl<Long, UserProfile> implements UserProfileRepository {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public UserProfile findByFirstAndLastNames(String firstName, String lastName) {
        try {
            String queryInStringFormat = "from " + entityClass.getName() + " where firstName =:firstName and lastName =:lastName";
            Query query = entityManager.createQuery(queryInStringFormat);
            query.setParameter(FIRST_NAME_QUERY_PARAMETER, firstName);
            query.setParameter(LAST_NAME_QUERY_PARAMETER, lastName);
            return (UserProfile) query.getSingleResult();
        } catch (NoResultException e) {
            logger.error(e.getMessage(), e);
            throw new GetUserByNamesRepositoryException("Can't get user profile by first and last names " +
                    "on repository level : first name = " + firstName + " last name = " + lastName, e);
        }
    }

    @Override
    public Optional<UserProfile> findByIdIfExist(Long id) {
        return Optional.ofNullable(entityManager.find(entityClass, id));
    }
}
