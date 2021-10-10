package com.epam.esm.service;

import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.dto.GiftCertificateQueryParamDto;

import java.util.List;

public interface GiftCertificateService {

    GiftCertificateDto add(GiftCertificateDto giftCertificateDto);

    List<GiftCertificateDto> findAll();

    GiftCertificateDto findById(long id);

    GiftCertificateDto findByName(String name);

    GiftCertificateDto update(GiftCertificateDto giftCertificateDto);

     List<GiftCertificateDto> findGiftCertificates(
            GiftCertificateQueryParamDto giftCertificateQueryParametersDto);

    void remove(long id);
}
