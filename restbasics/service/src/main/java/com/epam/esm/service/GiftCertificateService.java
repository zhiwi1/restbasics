package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateInputDto;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateQueryParamDto;

import java.util.List;

public interface GiftCertificateService extends BaseService<GiftCertificateDto, Long> {
    GiftCertificateDto create(GiftCertificateInputDto giftCertificateCreateDto);

    List<GiftCertificateDto> findGiftCertificates(
            GiftCertificateQueryParamDto giftCertificateQueryParametersDto);

    GiftCertificateDto update(Long id, GiftCertificateInputDto giftCertificateDto);

}
