package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;

import java.util.List;
import java.util.Optional;


public interface GiftCertificateDao extends BaseDao<GiftCertificate, Long> {

    List<GiftCertificate> findByQueryParameters(String query);

    GiftCertificate update(GiftCertificate giftCertificate);

    void updateLastDate(Long id);

    Optional<Long> findIdByTagId(Long tagId);

}