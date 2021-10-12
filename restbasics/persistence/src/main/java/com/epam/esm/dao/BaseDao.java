package com.epam.esm.dao;

import java.util.List;
import java.util.Optional;

public interface BaseDao<T, K> {
    List<T> findAll();

    Optional<T> findById(K id);

    Optional<T> findByName(String name);

    T create(T entity);

    void delete(K id);

}
