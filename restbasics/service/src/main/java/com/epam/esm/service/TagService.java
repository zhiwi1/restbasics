package com.epam.esm.service;

import com.epam.esm.service.dto.TagDto;

import java.util.List;
import java.util.Set;


public interface TagService {
    TagDto addTag(TagDto tagDto);

    List<TagDto> findAllTags();

    TagDto findTagById(long id);

     TagDto findTagByName(String name);

    void removeTag(long id);

    Set<TagDto> findTagsByGiftCertificateId(long giftCertificateId);
}

