package com.epam.esm.service;

import java.util.List;

public interface BaseService<T,K> {
    void delete(K id);

    T findById(K id);

    List<T> findAll();
}
