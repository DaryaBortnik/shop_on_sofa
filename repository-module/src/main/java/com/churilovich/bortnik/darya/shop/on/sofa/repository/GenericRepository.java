package com.churilovich.bortnik.darya.shop.on.sofa.repository;

import java.util.List;
import java.util.Optional;

public interface GenericRepository<I, E> {
    void persist(E entity);

    List<E> findAll();

    Optional<E> findById(I id);

    void merge(E entity);

    void remove(E entity);

    I getAmountOfEntities();
}
