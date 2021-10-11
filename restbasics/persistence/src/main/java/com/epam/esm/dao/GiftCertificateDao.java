package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface GiftCertificateDao extends BaseDao<GiftCertificate, Long> {
    List<GiftCertificate> findByQueryParameters(String query);

   GiftCertificate update(GiftCertificate giftCertificate);

    Optional<GiftCertificate> applyPatch(Map<String, Object> paramMap, Long id);
}