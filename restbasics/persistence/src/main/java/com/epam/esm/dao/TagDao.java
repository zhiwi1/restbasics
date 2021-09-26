package com.epam.esm.dao;

import com.epam.esm.controller.entity.Tag;

import java.util.List;

public interface TagDao {
    List<Tag> findAll();

    Tag findById(long id);

    Tag findByName(String name);

    void insert(Tag tag);

    void delete(long id) ;
}
