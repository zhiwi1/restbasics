package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateDao {
    List<GiftCertificate> findAll();

    Optional<GiftCertificate> findById(long id);

    Optional<GiftCertificate> findByName(String name);

    Optional<GiftCertificate> add(GiftCertificate giftCertificate);

    void delete(long id);

    Optional<GiftCertificate> update(GiftCertificate giftCertificate);

    List<GiftCertificate> findByQueryParameters(String query);
}
