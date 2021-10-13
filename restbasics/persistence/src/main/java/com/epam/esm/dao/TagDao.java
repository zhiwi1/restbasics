package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.List;

public interface TagDao extends BaseDao<Tag, Long> {

    List<Tag> findByCertificateId(Long giftCertificateId);

    void attachTag(Long tagId, Long certificateId);
}
