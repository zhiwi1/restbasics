package com.epam.esm.dao.impl;

import com.epam.esm.entity.GiftCertificate;

import static com.epam.esm.dao.impl.GiftCertificateDaoImpl.ParamColumn.*;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.mapper.GiftCertificateMapper;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;


import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Types;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;


@Repository
@RequiredArgsConstructor
@Slf4j
public class GiftCertificateDaoImpl implements GiftCertificateDao {

    private static final String SQL_FIND_ALL_CERTIFICATES =
            "select id, name, price,  create_date, last_update_date, duration,description " +
                    "from certificates ";
    private static final String SQL_FIND_CERTIFICATE_BY_ID =
            "select id, name, price,  create_date, last_update_date, duration,description " +
                    "from certificates  WHERE id=?";
    private static final String SQL_FIND_CERTIFICATE_BY_NAME =
            "select id, name, price,  create_date, last_update_date, duration " +
                    "from certificates  WHERE name =?";
    private static final String SQL_INSERT_CERTIFICATE =
            "insert into certificates(name, description, price, create_date, last_update_date, duration) " +
                    "values(?, ?, ?, ?, ?,?)";
    private static final String SQL_DELETE_CERTIFICATE =
            "delete from certificates where id = ?";
    private static final String SQL_UPDATE_CERTIFICATE =
            "update certificates " +
                    "set name = ?, price = ?, last_update_date = ?, duration = ?,description = ? " +
                    "where id = ?";
    private static final String SQL_FIND_ALL_BY_PARAM = "SELECT certificates.id,certificates.name, "
            + "description, price, duration, create_date, last_update_date FROM certificates "
            + "LEFT JOIN certificate_tags ON certificates.id = certificate_tags.certificate_id "
            + "LEFT JOIN tags ON certificate_tags.tag_id = tags.id ";

    private final JdbcTemplate jdbcTemplate;
    private final GiftCertificateMapper certificateMapper;


    @Override
    public Optional<GiftCertificate> findById(Long id) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            SQL_FIND_CERTIFICATE_BY_ID, new Object[]{id}, new int[]{Types.BIGINT}, certificateMapper
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            log.error("Zero rows were actually returns by id={}:{}", id, e.getMessage());
//todo good log
            log.error(String.format("Zero rows were actually returns by id=%d:%s", id, e.getMessage()));
        }
        return Optional.empty();
    }
    @Override
    public Optional<GiftCertificate> findByName(String name) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            SQL_FIND_CERTIFICATE_BY_NAME, new Object[]{name}, new int[]{Types.VARCHAR}, certificateMapper
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            log.error(String.format("Zero rows were actually returns by name= %s : %s", name, e.getMessage()));
        }
        return Optional.empty();
    }
    @Override
    public List<GiftCertificate> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL_CERTIFICATES, certificateMapper);
    }

    @Override
    public GiftCertificate create(GiftCertificate certificate) {
        certificate.setCreateDate(ZonedDateTime.now(ZoneId.systemDefault()));
        certificate.setLastUpdateDate(ZonedDateTime.now(ZoneId.systemDefault()));
        var keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT_CERTIFICATE, Statement.RETURN_GENERATED_KEYS);
            statement.setString(NAME_PARAM_ID, certificate.getName());
            statement.setString(DESCRIPTION_PARAM_ID, certificate.getDescription());
            statement.setBigDecimal(PRICE_PARAM_ID, certificate.getPrice());
            statement.setObject(CREATE_DATE_PARAM_ID, certificate.getCreateDate());
            statement.setObject(LAST_UPDATE_DATE_PARAM_ID, certificate.getLastUpdateDate());
            statement.setObject(DURATION_PARAM_ID, certificate.getDuration());
            return statement;
        }, keyHolder);
        if (keyHolder.getKey() != null) {
            certificate.setId(keyHolder.getKey().longValue());
        }
        return certificate;
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(
                SQL_DELETE_CERTIFICATE, new Object[]{id}, new int[]{Types.BIGINT}
        );
    }


    //todo method update patch побегать по мапке
    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        giftCertificate.setLastUpdateDate(ZonedDateTime.now(ZoneId.systemDefault()));
        try {
            jdbcTemplate.update(
                    SQL_UPDATE_CERTIFICATE,
                    new Object[]{giftCertificate.getName(),
                            giftCertificate.getPrice(), giftCertificate.getLastUpdateDate(),
                            giftCertificate.getDuration(), giftCertificate.getDescription(), giftCertificate.getId()},
                    new int[]{Types.VARCHAR, Types.DECIMAL, Types.TIMESTAMP, Types.INTEGER, Types.VARCHAR, Types.BIGINT});

        } catch (EmptyResultDataAccessException e) {
            log.error(String.format("Zero rows were actually returns:%s", e.getMessage()));
        }
        return giftCertificate;

    }

    @Override
    public GiftCertificate applyPatch(Map<String, Object> paramMap, Long id) {
        return null;
    }

    @Override
    public List<GiftCertificate> findByQueryParameters(String query) {
        return jdbcTemplate.query(SQL_FIND_ALL_BY_PARAM + query, certificateMapper);
    }

    @UtilityClass
   static class ParamColumn {
        public static final int NAME_PARAM_ID = 1;
        public static final int DESCRIPTION_PARAM_ID = 2;
        public static final int PRICE_PARAM_ID = 3;
        public static final int CREATE_DATE_PARAM_ID = 4;
        public static final int LAST_UPDATE_DATE_PARAM_ID = 5;
        public static final int DURATION_PARAM_ID = 6;

    }
}