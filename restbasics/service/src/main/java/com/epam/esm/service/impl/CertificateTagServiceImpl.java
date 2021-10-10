package com.epam.esm.service.impl;

import com.epam.esm.dao.CertificateTagDao;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.CertificateTagService;
import com.epam.esm.service.dto.CertificateTagDto;
import com.epam.esm.service.exception.ExceptionCode;
import com.epam.esm.service.exception.ExceptionMessageKey;
import com.epam.esm.service.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CertificateTagServiceImpl implements CertificateTagService {
    private final GiftCertificateDao giftCertificateDao;
    private final TagDao tagDao;
    private final CertificateTagDao certificateTagDao;


    @Transactional
    @Override
    public void addCertificateTag(CertificateTagDto certificateTagDto) {
        Optional<Tag> tag = tagDao.findById(certificateTagDto.getCertificateId());
        Optional<GiftCertificate> giftCertificate = giftCertificateDao.findById(certificateTagDto.getCertificateId());
        if (tag.isEmpty() || giftCertificate.isEmpty()) {
        throw  new ResourceNotFoundException
                    (ExceptionMessageKey.RESOURCE_NOT_FOUND_KEY,
                            ExceptionCode.RESOURCE_NOT_FOUND);
        }
        certificateTagDao.addTaggedCertificate(certificateTagDto.getTagId(), certificateTagDto.getCertificateId());

    }
}
