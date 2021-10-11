package com.epam.esm.mapper;

import static com.epam.esm.mapper.DatabaseColumnName.*;


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
    public GiftCertificate mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return GiftCertificate.builder()
                .id(resultSet.getLong(ID))
                .name(resultSet.getString(NAME))
                .description(resultSet.getString(DESCRIPTION))
                .price(resultSet.getObject(PRICE, BigDecimal.class))
                .duration(resultSet.getInt(DURATION))
                .createDate(convertToZoneDateTime(resultSet.getTimestamp(CREATE_DATE)))
                .lastUpdateDate(convertToZoneDateTime(resultSet.getTimestamp(LAST_UPDATE_DATE)))
                .build();
    }

    private ZonedDateTime convertToZoneDateTime(Timestamp createTimestamp) {
        return ZonedDateTime.ofInstant(createTimestamp.toInstant(), ZoneId.systemDefault());
    }
}