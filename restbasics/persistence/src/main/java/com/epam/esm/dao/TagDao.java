package com.epam.esm.dao;

import com.epam.esm.entity.CertificateTag;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDao {
    List<Tag> findAll();

    Optional<Tag> findByName(String name);

    Optional<Tag> insert(Tag tag);

    void delete(long id);

    List<Tag> findByGiftCertificateId(long giftCertificateId);

    Optional<Tag> findById(long id);
}
