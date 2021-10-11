package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

import lombok.RequiredArgsConstructor;
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
import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
@Slf4j
public class TagDaoImpl implements TagDao {

    private static final String SQL_FIND_ALL = "SELECT id,name FROM tags";

    private static final String SQL_FIND_BY_ID = "SELECT id,name FROM tags WHERE id=?";

    private static final String SQL_FIND_BY_NAME = "SELECT id,name FROM tags WHERE name=?";

    private static final String SQL_INSERT_TAG = "INSERT INTO tags(name) VALUES(?)";

    private static final String SQL_DELETE_TAG = "DELETE FROM tags WHERE id=?";

    private static final String SQL_FIND_BY_GIFT_CERTIFICATE_ID = "SELECT id, name FROM tags "
            + "INNER JOIN certificate_tags ON tags.id = certificate_tags.tag_id WHERE "
            + "certificate_id= ?";
    private static final String SQL_ATTACH_TAG = "INSERT IGNORE INTO certificate_tags(tag_id,certificate_id) VALUES(?,?)";

    private static final int NAME_PARAM_ID = 1;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, new BeanPropertyRowMapper<>(Tag.class));
    }

    @Override
    public Optional<Tag> findByName(String name) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            SQL_FIND_BY_ID, new Object[]{name}, new int[]{Types.INTEGER}, new BeanPropertyRowMapper<>()
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            log.error(String.format("Zero rows were actually returns by id=%s:%s", name, e.getMessage()));
        }
        return Optional.empty();
    }

    @Override
    public Tag create(Tag tag) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT_TAG, Statement.RETURN_GENERATED_KEYS);
            statement.setString(NAME_PARAM_ID, tag.getName());
            return statement;
        }, keyHolder);
        if (keyHolder.getKey() != null) {
            tag.setId(keyHolder.getKey().longValue());
        }
        return tag;
    }


    @Override
    public void delete(Long id) {
        jdbcTemplate.update(SQL_DELETE_TAG, id);
    }

    @Override
    public List<Tag> findByCertificateId(Long giftCertificateId) {
        return jdbcTemplate.query(SQL_FIND_BY_GIFT_CERTIFICATE_ID, new BeanPropertyRowMapper<>(Tag.class), new Object[]{giftCertificateId});
    }

    @Override
    public void attachTag(Long tagId, Long certificateId) {
        jdbcTemplate.update(SQL_ATTACH_TAG, tagId,certificateId);
    }


    @Override
    public Optional<Tag> findById(Long id) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            SQL_FIND_BY_ID, new Object[]{id}, new int[]{Types.INTEGER}, new BeanPropertyRowMapper<>()
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            log.error(String.format("Zero rows were actually returns by id=%d:%s", id, e.getMessage()));
        }
        return Optional.empty();
    }


}
