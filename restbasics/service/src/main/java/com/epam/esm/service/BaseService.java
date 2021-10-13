package com.epam.esm.service;

import java.util.List;

/**
 * The interface Base service.Base Interface for TagService and GiftCertificateService
 *
 * @param <T> the type parameter
 * @param <K> the type parameter
 */
public interface BaseService<T,K> {
    /**
     * Delete by id.
     *
     * @param id the id
     */
    void delete(K id);

    /**
     * Find by id(K) .
     *
     * @param id the id
     * @return the t
     */
    T findById(K id);

    /**
     * Find all elements and return them list.
     *
     * @return the list
     */
    List<T> findAll();
}
