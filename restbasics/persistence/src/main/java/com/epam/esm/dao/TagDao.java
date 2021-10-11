package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDao extends BaseDao<Tag, Long> {

    Optional<Tag> findByName(String name);

    List<Tag> findByCertificateId(Long giftCertificateId);

    void attachTag(Long tagId, Long certificateId);
}
