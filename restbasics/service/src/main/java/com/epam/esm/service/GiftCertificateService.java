package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateQueryParamDto;
import com.epam.esm.entity.GiftCertificate;

import java.util.List;

public interface GiftCertificateService extends BaseService<GiftCertificateDto, Long> {


    GiftCertificateDto findByName(String name);


    List<GiftCertificateDto> findGiftCertificates(
            GiftCertificateQueryParamDto giftCertificateQueryParametersDto);


}
