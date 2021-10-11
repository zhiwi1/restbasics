package com.epam.esm.service;

import com.epam.esm.dto.TagDto;

import java.util.List;
import java.util.Set;


public interface TagService extends BaseService<TagDto, Long> {

    TagDto findTagByName(String name);

    Set<TagDto> findTagsByGiftCertificateId(long giftCertificateId);
}

