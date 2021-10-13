package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dto.CertificateTagDto;
import com.epam.esm.dto.TagCreateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.dao.TagDao;
import com.epam.esm.exception.DublicateResourceException;
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
    private final GiftCertificateDao giftCertificateDao;
    private final ServiceTagMapper tagMapper;


    @Transactional
    @Override
    public TagDto create(TagCreateDto tagCreateDto) {
        Optional<Tag> existingTagOptional = tagDao.findByName(tagCreateDto.getName());
        if (existingTagOptional.isPresent()) {
            throw new DublicateResourceException(tagCreateDto.getName());
        }
        Tag tag = tagMapper.toEntity(tagCreateDto);
        Tag insertedTag = tagDao.create(tag);
        return tagMapper.toDto(insertedTag);
    }

    @Override
    public List<TagDto> findAll() {
        List<Tag> foundTags = tagDao.findAll();
        return foundTags.stream()
                .map(tagMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TagDto findById(Long id) {
        Optional<Tag> foundTagOptional = tagDao.findById(id);
        return foundTagOptional.map(tagMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Optional<Tag> tag = tagDao.findById(id);
        if (tag.isEmpty()) {
            throw new ResourceNotFoundException(id);
        }
        giftCertificateDao.findIdByTagId(id).ifPresent(giftCertificateDao::updateLastDate);
        tagDao.delete(id);
    }

    @Override
    public Set<TagDto> findTagsByGiftCertificateId(Long giftCertificateId) {
        List<Tag> tag = tagDao.findByCertificateId(giftCertificateId);
        return tag.stream().map(tagMapper::toDto).collect(Collectors.toSet());
    }

    @Transactional
    @Override
    public void attachTag(CertificateTagDto certificateTagDto) {
        Optional<Tag> tag = tagDao.findById(certificateTagDto.getCertificateId());
        Optional<GiftCertificate> giftCertificate = giftCertificateDao.findById(certificateTagDto.getCertificateId());
        if (tag.isEmpty() || giftCertificate.isEmpty()) {
            throw new ResourceNotFoundException(certificateTagDto.getCertificateId(), certificateTagDto.getTagId());
        }
        tagDao.attachTag(certificateTagDto.getTagId(), certificateTagDto.getCertificateId());
        giftCertificateDao.updateLastDate(certificateTagDto.getCertificateId());
    }
}
