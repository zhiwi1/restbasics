package com.epam.esm.dao.impl;

import com.epam.esm.controller.entity.GiftCertificate;
import com.epam.esm.dao.GiftCertificateDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


import java.util.*;


@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {

    private static final String SQL_FIND_ALL_CERTIFICATES =
            "select id, name, price,  create_date, last_update_date, duration " +
                    "from certificates ";

    private static final String SQL_FIND_CERTIFICATE_BY_ID =
            "select id, name, price,  create_date, last_update_date, duration " +
                    "from certificates  WHERE id=?";

    private static final String SQL_FIND_CERTIFICATE_BY_NAME =
            "select id, name, price,  create_date, last_update_date, duration " +
                    "from certificates  WHERE name =?";

    private static final String SQL_INSERT_CERTIFICATE =
            "insert into certificates(name,  price, create_date, last_update_date, duration) " +
                    "values(?, ?, ?, ?, ?)";

    private static final String SQL_DELETE_CERTIFICATE =
            "delete from certificates where id = ?";

    private static final String SQL_UPDATE_CERTIFICATE =
            "update certificates " +
                    "set name = ?, price = ?, last_update_date = ?, duration = ? " +
                    "where id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<GiftCertificate> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL_CERTIFICATES, new BeanPropertyRowMapper<>(GiftCertificate.class));
    }


    @Override
    public GiftCertificate findById(long id) {
        return jdbcTemplate.query(SQL_FIND_CERTIFICATE_BY_ID, new BeanPropertyRowMapper<>(GiftCertificate.class), new Object[]{id})
                .stream().findAny().orElse(null);
    }

    @Override
    public GiftCertificate findByName(String name) {
        return jdbcTemplate.query(SQL_FIND_CERTIFICATE_BY_NAME, new BeanPropertyRowMapper<>(GiftCertificate.class), new Object[]{name}).
                stream().findAny().orElse(null);
    }

    @Override
    public void insert(GiftCertificate giftCertificate) {
        jdbcTemplate.update(SQL_INSERT_CERTIFICATE, giftCertificate.getName(),
                giftCertificate.getPrice(), giftCertificate.getCreateDate(),giftCertificate.getLastUpdateDate(),
                giftCertificate.getDuration());
    }

    @Override
    public void delete(long id) {
        jdbcTemplate.update(SQL_DELETE_CERTIFICATE, id);
    }

    @Override
    public void update(long id, GiftCertificate giftCertificate) {
        jdbcTemplate.update(SQL_UPDATE_CERTIFICATE, giftCertificate.getName(),
                giftCertificate.getPrice(), giftCertificate.getLastUpdateDate(),
                giftCertificate.getDuration(), id);
    }

}