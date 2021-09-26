package com.epam.esm.service;

public interface CertificateService {
    Set<GiftCertificate> getAllCertificates(String sortParameter, String orderParameter);


    GiftCertificate getCertificateById(int id) throws ResourceNotFoundServiceException;


    GiftCertificate insertCertificate(GiftCertificate giftCertificate)
            throws ResourceConflictServiceException, InvalidEntityDataServiceException;

    GiftCertificate updateCertificate(int id, GiftCertificate giftCertificate)
            throws InvalidRequestDataServiceException, InvalidEntityDataServiceException,
            ResourceConflictServiceException;

    void deleteCertificate(int id) throws InvalidRequestDataServiceException;
}
