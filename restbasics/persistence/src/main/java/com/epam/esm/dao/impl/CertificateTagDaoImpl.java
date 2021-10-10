package com.epam.esm.dao.impl;

import com.epam.esm.dao.CertificateTagDao;
import com.epam.esm.dao.mapper.CertificateTagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CertificateTagDaoImpl implements CertificateTagDao {
    private static final String SQL_ADD_TAGGED_CERTIFICATE = "INSERT IGNORE INTO certificate_tags(tag_id,certificate_id) VALUES(?,?)";
    private static final String SQL_REMOVE_TAGGED_GIFT_CERTIFICATE = "DELETE FROM certificate_tags "
            + "WHERE tag_id = ?";
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CertificateTagDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addTaggedCertificate(long tagId, long certificateId) {
        jdbcTemplate.update(SQL_ADD_TAGGED_CERTIFICATE, tagId, certificateId);

    }
    @Override
    public void removeTaggedGiftCertificate(long id) {
        jdbcTemplate.update(SQL_REMOVE_TAGGED_GIFT_CERTIFICATE, id);
    }

}
