package com.epam.esm.dao.impl;

import com.epam.esm.entity.GiftCertificate;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.mapper.GiftCertificateMapper;
import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;


import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.*;


@Repository
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

    private final GiftCertificateMapper giftCertificateMapper;

    @Autowired
    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate, GiftCertificateMapper giftCertificateMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.giftCertificateMapper = giftCertificateMapper;
    }



    @Override
    public List<GiftCertificate> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL_CERTIFICATES, giftCertificateMapper);
    }

    @Override
    public Optional<GiftCertificate> findById(long id) {
        return jdbcTemplate.query(SQL_FIND_CERTIFICATE_BY_ID, giftCertificateMapper, new Object[]{id})
                .stream().findFirst();
    }

    @Override
    public Optional<GiftCertificate> findByName(String name) {
        return jdbcTemplate.query(SQL_FIND_CERTIFICATE_BY_NAME, new BeanPropertyRowMapper<>(GiftCertificate.class), new Object[]{name}).
                stream().findFirst();
    }


    @Override
    public Optional<GiftCertificate> add(GiftCertificate giftCertificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT_CERTIFICATE, Statement.RETURN_GENERATED_KEYS);
            statement.setString(ParamColumn.NAME_PARAM_ID, giftCertificate.getName());
            statement.setString(ParamColumn.DESCRIPTION_PARAM_ID, giftCertificate.getDescription());
            statement.setBigDecimal(ParamColumn.PRICE_PARAM_ID, giftCertificate.getPrice());
            statement.setObject(ParamColumn.CREATE_DATE_PARAM_ID, giftCertificate.getCreateDate());
            statement.setObject(ParamColumn.LAST_UPDATE_DATE_PARAM_ID, giftCertificate.getLastUpdateDate());
            statement.setObject(ParamColumn.DURATION_PARAM_ID, giftCertificate.getDuration());
            return statement;
        }, keyHolder);
        if (keyHolder.getKey() != null) {
            giftCertificate.setId(keyHolder.getKey().longValue());
        }
        return findByName(giftCertificate.getName());
    }

    @Override
    public void delete(long id) {
        jdbcTemplate.update(SQL_DELETE_CERTIFICATE, id);
    }

    @Override
    public Optional<GiftCertificate> update(GiftCertificate giftCertificate) {
        jdbcTemplate.update(SQL_UPDATE_CERTIFICATE, giftCertificate.getName(),
                giftCertificate.getPrice(), giftCertificate.getLastUpdateDate(),
                giftCertificate.getDuration(),giftCertificate.getDescription(), giftCertificate.getId());
        return findByName(giftCertificate.getName());
    }

    @Override
    public List<GiftCertificate> findByQueryParameters(String query) {

        return jdbcTemplate.query(SQL_FIND_ALL_BY_PARAM + query,giftCertificateMapper);
    }


    @UtilityClass
    private static class ParamColumn {
        private final int NAME_PARAM_ID = 1;
        private final int DESCRIPTION_PARAM_ID = 2;
        private final int PRICE_PARAM_ID = 3;
        private final int CREATE_DATE_PARAM_ID = 4;
        private final int LAST_UPDATE_DATE_PARAM_ID = 5;
        private final int DURATION_PARAM_ID = 6;

    }
}