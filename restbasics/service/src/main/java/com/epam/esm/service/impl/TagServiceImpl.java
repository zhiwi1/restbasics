package com.epam.esm.service.impl;

import com.epam.esm.controller.entity.Tag;
import com.epam.esm.dao.TagDao;
import com.epam.esm.service.TagService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class TagServiceImpl implements TagService {

    private TagDao tagDao;

    public TagServiceImpl(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public Set<Tag> findAllTags(String sortParameter, String orderParameter) {
        return null;
    }

    //todo set
    @Override
    public List<Tag> findAll() {
        return tagDao.findAll();

    }

    @Override
    public Tag findById(long id) {
        return tagDao.findById(id);
    }

    @Override
    public void deleteById(long id) {
        tagDao.delete(id);
    }

    @Override
    public void insert(Tag tag) {
        tagDao.insert(tag);
    }
}

