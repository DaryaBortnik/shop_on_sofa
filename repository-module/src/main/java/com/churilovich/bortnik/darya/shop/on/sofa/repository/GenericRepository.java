package com.churilovich.bortnik.darya.shop.on.sofa.repository;

import java.util.List;

public interface GenericRepository<I, E> {
    void persist(E entity);

    List<E> findAll();

    E findById(I id);

    void merge(E entity);

    void remove(E entity);

    I getAmountOfEntities();
}
