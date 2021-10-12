package com.epam.esm.service.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.dao.TagDao;
import com.epam.esm.exception.DublicateResourceException;
import com.epam.esm.exception.ExceptionCode;
import com.epam.esm.exception.ExceptionMessageKey;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.ServiceTagMapper;
import com.epam.esm.service.TagService;
import com.epam.esm.dto.TagDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagDao tagDao;
    private final ServiceTagMapper tagMapper;

    @Transactional
    @Override
    public TagDto addTag(TagDto tagDto) {
        Optional<Tag> existingTagOptional = tagDao.findByName(tagDto.getName());
        if (existingTagOptional.isPresent()) {
            throw new DublicateResourceException(
                    ExceptionMessageKey.INCORRECT_PARAMETER_VALUE_KEY,
                    ExceptionCode.INCORRECT_PARAMETER_VALUE);
        }
        Tag tag = tagMapper.toEntity(tagDto);
        Tag insertedTag = tagDao.insert(tag).orElseThrow(() -> new ResourceNotFoundException
                (ExceptionMessageKey.INCORRECT_PARAMETER_VALUE_KEY,
                        ExceptionCode.INCORRECT_PARAMETER_VALUE));
        return tagMapper.toDto(insertedTag);
    }

    @Override
    public List<TagDto> findAllTags() {
        List<Tag> foundTags = tagDao.findAll();
        return foundTags.stream()
                .map(tagMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TagDto findById(long id) {
        Optional<Tag> foundTagOptional = tagDao.findById(id);
        return foundTagOptional.map(tagMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException
                        (ExceptionMessageKey.RESOURCE_NOT_FOUND_KEY,
                                ExceptionCode.RESOURCE_NOT_FOUND));
    }

    @Override
    public TagDto findTagByName(String name) {
        Optional<Tag> foundTagOptional = tagDao.findByName(name);
        return foundTagOptional.map(tagMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException
                        (ExceptionMessageKey.RESOURCE_NOT_FOUND_KEY,
                                ExceptionCode.RESOURCE_NOT_FOUND));
    }

    @Transactional
    @Override
    public void removeTag(long id) {
        tagDao.findById(id).orElseThrow(() -> new ResourceNotFoundException
                (ExceptionMessageKey.INCORRECT_PARAMETER_VALUE_KEY,
                        ExceptionCode.INCORRECT_PARAMETER_VALUE));
        tagDao.delete(id);
    }

    @Override
    public Set<TagDto> findTagsByGiftCertificateId(long giftCertificateId) {
        List<Tag> tag = tagDao.findByGiftCertificateId(giftCertificateId);
        return tag.stream().map(tagMapper::toDto).collect(Collectors.toSet());
    }
}
