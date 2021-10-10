package com.epam.esm.dao.mapper;

import com.epam.esm.entity.CertificateTag;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.esm.dao.mapper.DatabaseColumnName.*;


@Component
public class CertificateTagMapper implements RowMapper<CertificateTag> {
    @Override
    public CertificateTag mapRow(ResultSet rs, int rowNum) throws SQLException {
        return CertificateTag.builder()
                .tagId(rs.getLong(TAG_ID))
                .certificateId(rs.getLong(CERTIFICATE_ID))
                .build();
    }
}