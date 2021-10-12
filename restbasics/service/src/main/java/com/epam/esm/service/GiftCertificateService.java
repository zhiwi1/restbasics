package com.epam.esm.service;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateQueryParamDto;
import com.epam.esm.entity.GiftCertificate;

import java.util.List;

public interface GiftCertificateService extends BaseService<GiftCertificateDto, Long> {

    List<GiftCertificateDto> findGiftCertificates(
            GiftCertificateQueryParamDto giftCertificateQueryParametersDto);

    GiftCertificateDto update(GiftCertificateDto certificateDto);

    GiftCertificateDto applyPatch(GiftCertificateDto certificateDto);
}
