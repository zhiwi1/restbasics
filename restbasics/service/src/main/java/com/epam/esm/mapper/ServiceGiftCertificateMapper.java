package com.epam.esm.mapper;

import com.epam.esm.dto.GiftCertificateInputDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ServiceGiftCertificateMapper {
    private final ModelMapper modelMapper;
    private final ServiceTagMapper tagMapper;


    public GiftCertificate toEntity(GiftCertificateDto giftCertificateDTO) {
        GiftCertificate giftCertificate = modelMapper.map(giftCertificateDTO, GiftCertificate.class);
        if (giftCertificateDTO.getTags() != null) {
            Set<Tag> tags = (giftCertificateDTO.getTags().stream()
                    .map(tagMapper::toEntity).collect(Collectors.toSet()));
            giftCertificate.setTags(tags);
        }
        return giftCertificate;
    }
    public GiftCertificate toEntity(GiftCertificateInputDto giftCertificateCreateDto) {
        return modelMapper.map(giftCertificateCreateDto, GiftCertificate.class);
    }

    public GiftCertificateDto toDto(GiftCertificate giftCertificate) {
        GiftCertificateDto giftCertificateDto = modelMapper.map(giftCertificate, GiftCertificateDto.class);
        if (giftCertificate.getTags() != null) {
            Set<TagDto> tags = (giftCertificate.getTags().stream()
                    .map(tagMapper::toDto).collect(Collectors.toSet()));
            giftCertificateDto.setTags(tags);
        }
        return giftCertificateDto;
    }
}