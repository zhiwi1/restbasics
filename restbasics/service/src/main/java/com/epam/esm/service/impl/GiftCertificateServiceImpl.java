package com.epam.esm.service.impl;

import com.epam.esm.controller.entity.GiftCertificate;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private GiftCertificateDao certificateDao;

    public GiftCertificateServiceImpl(GiftCertificateDao certificateDao) {
        this.certificateDao = certificateDao;
    }

    @Override
    public List<GiftCertificate> findAll() {
        return certificateDao.findAll();
    }

    @Override
    public GiftCertificate findById(long id) {
        return certificateDao.findById(id);
    }

    @Override
    public void deleteById(long id) {
        certificateDao.delete(id);
    }

    @Override
    public void delete(GiftCertificate certificate) {

    }

    @Override
    public void insert(GiftCertificate certificate) {

    }

    @Override
    public void update(long id, GiftCertificate certificate) {

    }
}
