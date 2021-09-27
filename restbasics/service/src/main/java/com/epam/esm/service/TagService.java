package com.epam.esm.service;

import com.epam.esm.controller.entity.Tag;

import java.util.List;
import java.util.Set;

public interface TagService {
    Set<Tag> findAllTags(String sortParameter, String orderParameter);

    List<Tag> findAll();

    Tag findById(long id);

    void deleteById(long id);

    void insert(Tag tag);
}
