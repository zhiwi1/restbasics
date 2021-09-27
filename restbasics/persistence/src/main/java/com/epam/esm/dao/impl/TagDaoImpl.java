package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.controller.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TagDaoImpl implements TagDao {

    private static final String SQL_FIND_ALL = "SELECT id,name FROM tags";

    private static final String SQL_FIND_BY_ID = "SELECT id,name FROM tags WHERE id=?";

    private static final String SQL_FIND_BY_NAME = "SELECT id,name FROM tags WHERE name=?";

    private static final String SQL_INSERT_TAG = "INSERT INTO tags(id,name) VALUES(?,?)";

    private static final String SQL_DELETE_TAG = "DELETE FROM tags WHERE id=?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, new BeanPropertyRowMapper<>(Tag.class));
    }
//mapper custom, package mapper
    @Override
    public Tag findById(long id) {
        return jdbcTemplate.query(SQL_FIND_BY_ID, new BeanPropertyRowMapper<>(Tag.class), new Object[]{id})
                .stream().findAny().orElse(null);
    }

    // todo new  error + join
    @Override
    public Tag findByName(String name) {
        return jdbcTemplate.query(SQL_FIND_BY_NAME, new BeanPropertyRowMapper<>(Tag.class), new Object[]{name})
                .stream().findAny().orElse(null);
    }

    @Override
    public void insert(Tag tag) {
        jdbcTemplate.update(SQL_INSERT_TAG, tag.getId(), tag.getName());
    }

    @Override
    public void delete(long id) {
        jdbcTemplate.update(SQL_DELETE_TAG, id);
    }

}
