package com.epam.esm.service;

import com.epam.esm.controller.entity.GiftCertificate;

import java.util.List;


public interface GiftCertificateService {
    List<GiftCertificate> findAll();

    GiftCertificate findById(long id);

    void deleteById(long id);

    void delete(GiftCertificate certificate);

    void insert(GiftCertificate certificate);

    void update(long id, GiftCertificate certificate);
}
