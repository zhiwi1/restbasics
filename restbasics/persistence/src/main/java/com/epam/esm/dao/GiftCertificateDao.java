package com.epam.esm.dao;

import com.epam.esm.controller.entity.GiftCertificate;

import java.util.List;

public interface GiftCertificateDao {
    public List<GiftCertificate> findAll();

    public GiftCertificate findById(long id);

    public GiftCertificate findByName(String name);

    public void insert(GiftCertificate giftCertificate);

    public void delete(long id);

    public void update(long id, GiftCertificate giftCertificate);
}
