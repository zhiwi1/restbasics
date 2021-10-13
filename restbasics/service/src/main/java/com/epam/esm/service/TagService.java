package com.epam.esm.service;

import com.epam.esm.dto.CertificateTagDto;
import com.epam.esm.dto.TagCreateDto;
import com.epam.esm.dto.TagDto;

import java.util.Set;


/**
 * The interface Tag service.
 */
public interface TagService extends BaseService<TagDto, Long> {
    /**
     * Find set of tagDto by gift certificate id .
     *
     * @param giftCertificateId the gift certificate id
     * @return the set of TagDto
     */
    Set<TagDto> findTagsByGiftCertificateId(Long giftCertificateId);

    /**
     * Attach tag to certificate.
     *
     * @param certificateTagDto the certificate tag dto
     */
    void attachTag(CertificateTagDto certificateTagDto);

    /**
     * Create tag dto.
     *
     * @param tagCreateDto the tag create dto
     * @return the tag dto
     */
    TagDto create(TagCreateDto tagCreateDto);
}

