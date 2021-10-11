package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;

import java.util.List;

public interface BaseService<T,K> {
    T create(T dto);

    T update(T dto);

    void delete(K id);

    T findById(K id);

    List<T> findAll();
}
