package com.churilovich.bortnik.darya.shop.on.sofa.repository.impl;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.GenericRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.exception.GetEntitiesAmountRepositoryException;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.exception.PersistEntityRepositoryException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

public abstract class GenericRepositoryImpl<I, E> implements GenericRepository<I, E> {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    protected Class<E> entityClass;

    @PersistenceContext
    protected EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public GenericRepositoryImpl() {
        ParameterizedType genericClass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<E>) genericClass.getActualTypeArguments()[1];
    }

    @Override
    public void persist(E entity) {
        try {
            entityManager.persist(entity);
        } catch (EntityExistsException e) {
            logger.error(e.getMessage(), e);
            throw new PersistEntityRepositoryException("Can't persist element because of its existing : " +
                    "element = " + entity, e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<E> findAll() {
        String queryInStringFormat = "from " + entityClass.getName();
        Query query = entityManager.createQuery(queryInStringFormat);
        return query.getResultList();
    }

    @Override
    public Optional<E> findById(I id) {
        return Optional.ofNullable(entityManager.find(entityClass, id));
    }

    @Override
    public void merge(E entity) {
        entityManager.merge(entity);
    }

    @Override
    public void remove(E entity) {
        entityManager.remove(entity);
    }

    @Override
    @SuppressWarnings("unchecked")
    public I getAmountOfEntities() {
        try {
            String queryInStringFormat = "select count(*) from " + entityClass.getName();
            Query query = entityManager.createQuery(queryInStringFormat);
            return (I) query.getSingleResult();
        } catch (NoResultException e) {
            logger.error(e.getMessage(), e);
            throw new GetEntitiesAmountRepositoryException("Can't get amount of elements on repository level : " +
                    "elements in " + entityClass.getSimpleName(), e);
        }
    }
}

