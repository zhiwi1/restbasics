package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.GiftCertificateInputDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DublicateResourceException;
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

import java.util.Optional;
import java.util.List;
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
    public GiftCertificateDto create(GiftCertificateInputDto giftCertificateCreateDto) {
        Optional<GiftCertificate> existingCertificate = giftCertificateDao.findByName(giftCertificateCreateDto.getName());
        if (existingCertificate.isPresent()) {
            throw new DublicateResourceException(giftCertificateCreateDto.getName());
        }
        GiftCertificate certificate = certificateMapper.toEntity(giftCertificateCreateDto);
        GiftCertificate addedCertificate = giftCertificateDao.create(certificate);
        return certificateMapper.toDto(addedCertificate);

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
    public GiftCertificateDto findById(Long id) {
        Optional<GiftCertificate> foundGiftCertificate = giftCertificateDao.findById(id);
        return foundGiftCertificate
                .map(this::convertGiftCertificateAndSetTags)
                .orElseThrow(() -> new ResourceNotFoundException(id));
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
    public GiftCertificateDto update(Long id, GiftCertificateInputDto giftCertificateDto) {
        GiftCertificateDto foundGiftCertificateDto = findById(id);
        updateFields(foundGiftCertificateDto, giftCertificateDto);
        GiftCertificate foundGiftCertificate = certificateMapper.toEntity(foundGiftCertificateDto);
        GiftCertificate updatedGiftCertificate = giftCertificateDao.update(foundGiftCertificate);
        return certificateMapper.toDto(updatedGiftCertificate);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Optional<GiftCertificate> certificate = giftCertificateDao.findById(id);
        if (certificate.isEmpty()) {
            throw new ResourceNotFoundException(id);
        }
        giftCertificateDao.delete(id);
    }

    private GiftCertificateDto convertGiftCertificateAndSetTags(GiftCertificate giftCertificate) {
        GiftCertificateDto giftCertificateDto = certificateMapper.toDto(giftCertificate);
        List<Tag> listOfTags = tagDao.findByCertificateId(giftCertificate.getId());
        Set<TagDto> set = listOfTags.stream().map(tagMapper::toDto).collect(Collectors.toSet());
        giftCertificateDto.setTags(set);
        return giftCertificateDto;
    }


    private void updateFields(GiftCertificateDto foundCertificate, GiftCertificateInputDto receivedGiftCertificate) {
        Optional.ofNullable(receivedGiftCertificate.getName()).ifPresent(foundCertificate::setName);
        Optional.ofNullable(receivedGiftCertificate.getDescription()).ifPresent(foundCertificate::setDescription);
        Optional.ofNullable(receivedGiftCertificate.getPrice()).ifPresent(foundCertificate::setPrice);
        Optional.of(receivedGiftCertificate.getDuration()).filter(duration -> duration != 0).ifPresent(foundCertificate::setDuration);
    }
}

