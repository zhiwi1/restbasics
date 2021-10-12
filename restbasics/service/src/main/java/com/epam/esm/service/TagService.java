package com.epam.esm.service;

import com.epam.esm.dto.CertificateTagDto;
import com.epam.esm.dto.TagDto;

import java.util.Set;


public interface TagService extends BaseService<TagDto, Long> {
    Set<TagDto> findTagsByGiftCertificateId(Long giftCertificateId);

    void attachTag(CertificateTagDto certificateTagDto);
}

