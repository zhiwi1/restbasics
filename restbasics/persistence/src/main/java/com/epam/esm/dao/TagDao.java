package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.List;

/**
 * The interface Tag dao.
 */
public interface TagDao extends BaseDao<Tag, Long> {

    /**
     * Find by certificate id list of tags.
     *
     * @param giftCertificateId the gift certificate id
     * @return the list of tags
     */
    List<Tag> findByCertificateId(Long giftCertificateId);

    /**
     * Attach tag to certificate.
     *
     * @param tagId         the tag id
     * @param certificateId the certificate id
     */
    void attachTag(Long tagId, Long certificateId);
}
