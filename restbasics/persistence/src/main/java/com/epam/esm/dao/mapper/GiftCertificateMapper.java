package com.epam.esm.dao.mapper;

import static com.epam.esm.dao.mapper.DatabaseColumnName.*;


import com.epam.esm.entity.GiftCertificate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
public class GiftCertificateMapper implements RowMapper<GiftCertificate> {
    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        GiftCertificate giftCertificate = GiftCertificate.builder()
                .id(rs.getLong(ID))
                .name(rs.getString(NAME))
                .description(rs.getString(DESCRIPTION))
                .price(rs.getObject(PRICE, BigDecimal.class))
                .duration(rs.getInt(DURATION))
                .build();
        Timestamp createTimestamp = rs.getTimestamp(CREATE_DATE);
        if (createTimestamp != null) {
            ZonedDateTime createDate = ZonedDateTime.ofInstant(createTimestamp.toInstant(), ZoneId.systemDefault());
            giftCertificate.setCreateDate(createDate);
        }
        Timestamp lastUpdateTimestamp = rs.getTimestamp(LAST_UPDATE_DATE);
        if (lastUpdateTimestamp != null) {
            ZonedDateTime updateDateTime = ZonedDateTime.ofInstant(lastUpdateTimestamp.toInstant(), ZoneId.systemDefault());
            giftCertificate.setLastUpdateDate(updateDateTime);
        }
        return giftCertificate;
    }
}