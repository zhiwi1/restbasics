package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DublicateResourceException;
import com.epam.esm.exception.ExceptionCode;
import com.epam.esm.exception.ExceptionMessageKey;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.ServiceGiftCertificateMapper;
import com.epam.esm.mapper.ServiceTagMapper;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateQueryParamDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.util.GiftCertificateQueryCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.ZonedDateTime;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateDao giftCertificateDao;
    private final TagDao tagDao;
    private final ServiceGiftCertificateMapper certificateMapper;
    private final ServiceTagMapper tagMapper;


    @Transactional
    @Override
    public GiftCertificateDto create(GiftCertificateDto giftCertificateDto) {
        Optional<Tag> existingTagOptional = tagDao.findByName(giftCertificateDto.getName());
        if (existingTagOptional.isPresent()) {
            //id передать
            throw new DublicateResourceException();
        }
        GiftCertificate giftCertificate = certificateMapper.toEntity(giftCertificateDto);
        GiftCertificate addedGiftCertificate = giftCertificateDao.create(giftCertificate);
        addedGiftCertificate.map(certificateMapper::toDto).orElseThrow(() -> new ResourceNotFoundException
                (ExceptionMessageKey.RESOURCE_NOT_FOUND_KEY,
                        ExceptionCode.RESOURCE_NOT_FOUND));
        attachTagToCertificate(giftCertificateDto);
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
        attachTagToCertificate(giftCertificateDto);
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
    public void delete(Long id) {
        //classical if
        giftCertificateDao.findById(id).orElseThrow(() -> new ResourceNotFoundException
                (ExceptionMessageKey.INCORRECT_PARAMETER_VALUE_KEY,
                        ExceptionCode.INCORRECT_PARAMETER_VALUE));
        //reten boolean jdbc template delete
        giftCertificateDao.delete(id);
    }

    private void attachTagToCertificate(GiftCertificateDto giftCertificateDto) {
        Set<TagDto> tags = new HashSet<>();
        if (giftCertificateDto.getTags() != null) {
            Set<TagDto> tagsSet = giftCertificateDto.getTags()
                    .stream()
                    .map(tagMapper::toEntity)
                    .map(tagDao::create)
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

