package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
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
import java.util.HashSet;
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
        Optional<GiftCertificate> existingCertificate = giftCertificateDao.findByName(giftCertificateDto.getName());
        if (existingCertificate.isPresent()) {
            throw new DublicateResourceException(giftCertificateDto.getName());
        }
        GiftCertificate giftCertificate = certificateMapper.toEntity(giftCertificateDto);
        GiftCertificate addedGiftCertificate = giftCertificateDao.create(giftCertificate);
        certificateMapper.toDto(addedGiftCertificate);
        attachTagsToCertificate(giftCertificateDto);
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
    public GiftCertificateDto update(GiftCertificateDto giftCertificateDto) {
        attachTagsToCertificate(giftCertificateDto);
        GiftCertificateDto foundGiftCertificateDto = findById(giftCertificateDto.getId());
        updateFields(foundGiftCertificateDto, giftCertificateDto);
        GiftCertificate foundGiftCertificate = certificateMapper.toEntity(foundGiftCertificateDto);
        GiftCertificate updatedGiftCertificate = giftCertificateDao.update(foundGiftCertificate);
        return certificateMapper.toDto(updatedGiftCertificate);
    }

    @Transactional
    @Override
    public GiftCertificateDto applyPatch(GiftCertificateDto giftCertificateDto) {
//        attachTagsToCertificate(giftCertificateDto);
//        GiftCertificateDto foundGiftCertificateDto = findById(giftCertificateDto.getId());
//        Map<String,Object> fields=new HashMap<>();
//        fields.put("name",foundGiftCertificateDto.getName());
//        fields.put("description",giftCertificateDto.getDescription());
//        fields.put("duration",giftCertificateDto.getDuration());
//        fields.put("price",giftCertificateDto.getPrice());
//        Stream.of(fields).forEach();
//        GiftCertificate foundGiftCertificate = certificateMapper.toEntity(foundGiftCertificateDto);
//        giftCertificateDao.applyPatch()
//        GiftCertificate updatedGiftCertificate = giftCertificateDao.update(foundGiftCertificate);
        return giftCertificateDto;
    }


    @Transactional
    @Override
    public void delete(Long id) {
        Optional<GiftCertificate> certificate = giftCertificateDao.findById(id);
        if (certificate.isEmpty()) {
            throw new ResourceNotFoundException(id);
        }
        giftCertificateDao.delete(id);
        //todo reten boolean jdbc template delete

    }

    private void attachTagsToCertificate(GiftCertificateDto giftCertificateDto) {
        Set<TagDto> tags = new HashSet<>();
        if (giftCertificateDto.getTags() != null) {
            Set<TagDto> tagsSet = giftCertificateDto.getTags()
                    .stream()
                    .map(tagMapper::toEntity)
                    .map(tagDao::create)
                    .map(tagMapper::toDto)
                    .collect(Collectors.toSet());
            tags.addAll(tagsSet);
        }
        giftCertificateDto.setTags(tags);
    }

    private GiftCertificateDto convertGiftCertificateAndSetTags(GiftCertificate giftCertificate) {
        GiftCertificateDto giftCertificateDto = certificateMapper.toDto(giftCertificate);
        List<Tag> listOfTags = tagDao.findByCertificateId(giftCertificate.getId());
        Set<TagDto> set = listOfTags.stream().map(tagMapper::toDto).collect(Collectors.toSet());
        giftCertificateDto.setTags(set);
        return giftCertificateDto;
    }


    private void updateFields(GiftCertificateDto foundCertificate, GiftCertificateDto receivedGiftCertificate) {
       Optional.ofNullable(receivedGiftCertificate.getName()).ifPresent(foundCertificate::setName);
       //stream
        if (receivedGiftCertificate.getDuration() != 0) {

            foundCertificate.setDuration(receivedGiftCertificate.getDuration());
        }
        if (receivedGiftCertificate.getDescription() != null) {
            foundCertificate.setDescription(receivedGiftCertificate.getDescription());
        }
        if (receivedGiftCertificate.getPrice() != null) {
            foundCertificate.setPrice(receivedGiftCertificate.getPrice());
        }
        foundCertificate.setTags(receivedGiftCertificate.getTags());
    }
}

