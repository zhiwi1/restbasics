package com.epam.esm.dao;


public interface CertificateTagDao {
    void addTaggedCertificate(long tagId, long certificateId);

    void removeTaggedGiftCertificate(long id);
}
