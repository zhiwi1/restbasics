package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateInputDto;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateQueryParamDto;

import java.util.List;

/**
 * The interface Gift certificate service.
 */
public interface GiftCertificateService extends BaseService<GiftCertificateDto, Long> {
    /**
     * Create gift certificate dto.
     *
     * @param giftCertificateInputDto the gift certificate create dto
     * @return the gift certificate dto (created element)
     */
    GiftCertificateDto create(GiftCertificateInputDto giftCertificateInputDto);

    /**
     * Find gift certificates list by query param
     *
     * @param giftCertificateQueryParametersDto the gift certificate query parameters dto
     * @return the list
     */
    List<GiftCertificateDto> findGiftCertificates(
            GiftCertificateQueryParamDto giftCertificateQueryParametersDto);

    /**
     * Update gift certificate dto.
     *
     * @param id                 the id
     * @param giftCertificateDto the gift certificate dto
     * @return the gift certificate dto
     */
    GiftCertificateDto update(Long id, GiftCertificateInputDto giftCertificateDto);

}
