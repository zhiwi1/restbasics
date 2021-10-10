package com.epam.esm.service.mapper;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.dto.TagDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ServiceGiftCertificateMapper {
    private final ModelMapper modelMapper;
    private final ServiceTagMapper tagMapper;

    @Autowired
    public ServiceGiftCertificateMapper(ModelMapper modelMapper, ServiceTagMapper tagMapper) {
        this.modelMapper = modelMapper;
        this.tagMapper=tagMapper;
    }

    public GiftCertificate toEntity(GiftCertificateDto giftCertificateDTO) {
        GiftCertificate giftCertificate = modelMapper.map(giftCertificateDTO,GiftCertificate.class);
        if (giftCertificateDTO.getTags() != null) {
            Set<Tag> tags = (giftCertificateDTO.getTags().stream()
                    .map(tagMapper::toEntity).collect(Collectors.toSet()));
            giftCertificate.setTags(tags);
        }
        return giftCertificate;
    }

    public GiftCertificateDto toDto(GiftCertificate giftCertificate) {
        GiftCertificateDto giftCertificateDto = modelMapper.map(giftCertificate,GiftCertificateDto.class);
        if (giftCertificate.getTags() != null) {
            Set<TagDto> tags = (giftCertificate.getTags().stream()
                    .map(tagMapper::toDto).collect(Collectors.toSet()));
            giftCertificateDto.setTags(tags);
        }
        return giftCertificateDto;
    }
}