package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.dto.GiftCertificateQueryParamDto;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.ExceptionCode;
import com.epam.esm.service.exception.ExceptionMessageKey;
import com.epam.esm.service.exception.ResourceNotFoundException;
import com.epam.esm.service.mapper.ServiceGiftCertificateMapper;
import com.epam.esm.service.mapper.ServiceTagMapper;
import com.epam.esm.service.util.GiftCertificateQueryCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.ZonedDateTime;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateDao giftCertificateDao;
    private final TagDao tagDao;
    private final ServiceGiftCertificateMapper certificateMapper;
    private final ServiceTagMapper tagMapper;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao, TagDao tagDao, ServiceGiftCertificateMapper certificateMapper, ServiceTagMapper tagMapper) {
        this.giftCertificateDao = giftCertificateDao;
        this.tagDao = tagDao;
        this.certificateMapper = certificateMapper;
        this.tagMapper = tagMapper;
    }

    @Transactional
    @Override
    public GiftCertificateDto add(GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = certificateMapper.toEntity(giftCertificateDto);
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        giftCertificate.setCreateDate(zonedDateTime);
        giftCertificate.setLastUpdateDate(zonedDateTime);
        Optional<GiftCertificate> addedGiftCertificate = giftCertificateDao.add(giftCertificate);
        addedGiftCertificate.map(certificateMapper::toDto).orElseThrow(() -> new ResourceNotFoundException
                (ExceptionMessageKey.RESOURCE_NOT_FOUND_KEY,
                        ExceptionCode.RESOURCE_NOT_FOUND));
        addAndSetTags(giftCertificateDto);
        return giftCertificateDto;
    }

    @Override
    public List<GiftCertificateDto> findAll() {
        List<GiftCertificate> foundGiftCertificates
                = giftCertificateDao.findAll();

        return foundGiftCertificates.stream()
                .map(this::convertGiftCertificateAndSetTags)
                .collect(Collectors.toList());
    }

    @Override
    public GiftCertificateDto findById(long id) {
        Optional<GiftCertificate> foundGiftCertificate = giftCertificateDao.findById(id);
        return foundGiftCertificate
                .map(this::convertGiftCertificateAndSetTags)
                .orElseThrow(() -> new ResourceNotFoundException
                        (ExceptionMessageKey.RESOURCE_NOT_FOUND_KEY,
                                ExceptionCode.RESOURCE_NOT_FOUND));
    }


    @Override
    public GiftCertificateDto findByName(String name) {
        Optional<GiftCertificate> foundGiftCertificate = giftCertificateDao.findByName(name);
        return foundGiftCertificate
                .map(this::convertGiftCertificateAndSetTags)
                .orElseThrow(() -> new ResourceNotFoundException
                        (ExceptionMessageKey.RESOURCE_NOT_FOUND_KEY,
                                ExceptionCode.RESOURCE_NOT_FOUND));
    }

    @Override
    public List<GiftCertificateDto> findGiftCertificates(
            GiftCertificateQueryParamDto giftCertificateQueryParametersDto) {

        List<GiftCertificate> foundGiftCertificates
                = giftCertificateDao.findByQueryParameters(GiftCertificateQueryCreator.createQuery(giftCertificateQueryParametersDto));
        return foundGiftCertificates.stream()
                .map(this::convertGiftCertificateAndSetTags)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public GiftCertificateDto update(GiftCertificateDto giftCertificateDto) {
        addAndSetTags(giftCertificateDto);
        GiftCertificateDto foundGiftCertificateDto = findById(giftCertificateDto.getId());
        updateFields(foundGiftCertificateDto, giftCertificateDto);
        GiftCertificate foundGiftCertificate = certificateMapper.toEntity(foundGiftCertificateDto);
        Optional<GiftCertificate> updatedGiftCertificate = giftCertificateDao.update(foundGiftCertificate);
        return updatedGiftCertificate.map(certificateMapper::toDto).orElseThrow(() -> new ResourceNotFoundException
                (ExceptionMessageKey.RESOURCE_NOT_FOUND_KEY,
                        ExceptionCode.RESOURCE_NOT_FOUND));
    }

    @Transactional
    @Override
    public void remove(long id) {
        giftCertificateDao.findById(id).orElseThrow(() -> new ResourceNotFoundException
                (ExceptionMessageKey.INCORRECT_PARAMETER_VALUE_KEY,
                        ExceptionCode.INCORRECT_PARAMETER_VALUE));
        giftCertificateDao.delete(id);
    }

    private void addAndSetTags(GiftCertificateDto giftCertificateDto) {
        Set<TagDto> tags = new HashSet<>();
        if (giftCertificateDto.getTags() != null) {
            Set<TagDto> tagsSet = giftCertificateDto.getTags()
                    .stream()
                    .map(tagMapper::toEntity)
                    .map(tagDao::insert)
                    .map(Optional::get)
                    .map(tagMapper::toDto)
                    .collect(Collectors.toSet());
            tags.addAll(tagsSet);
        }
        giftCertificateDto.setTags(tags);
    }

    private GiftCertificateDto convertGiftCertificateAndSetTags(GiftCertificate giftCertificate) {
        GiftCertificateDto giftCertificateDto = certificateMapper.toDto(giftCertificate);
        List<Tag> listOfTags = tagDao.findByGiftCertificateId(giftCertificate.getId());
        Set<TagDto> set = listOfTags.stream().map(tagMapper::toDto).collect(Collectors.toSet());
        giftCertificateDto.setTags(set);
        return giftCertificateDto;
    }


    private void updateFields(GiftCertificateDto foundGiftCertificate, GiftCertificateDto receivedGiftCertificate) {
        if (receivedGiftCertificate.getDuration() != 0) {
            foundGiftCertificate.setDuration(receivedGiftCertificate.getDuration());
        }
        if (receivedGiftCertificate.getName() != null) {
            foundGiftCertificate.setName(receivedGiftCertificate.getName());
        }
        if (receivedGiftCertificate.getDescription() != null) {
            foundGiftCertificate.setDescription(receivedGiftCertificate.getDescription());
        }
        if (receivedGiftCertificate.getPrice() != null) {
            foundGiftCertificate.setPrice(receivedGiftCertificate.getPrice());
        }
        foundGiftCertificate.setLastUpdateDate(ZonedDateTime.now());
        foundGiftCertificate.setTags(receivedGiftCertificate.getTags());
    }
}

