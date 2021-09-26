package com.epam.esm.controller;

import com.epam.esm.config.DataSourceConfig;
import com.epam.esm.controller.entity.Tag;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.impl.TagDaoImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(DataSourceConfig.class);
        TagDao tagDao = appContext.getBean("tagDaoImpl", TagDaoImpl.class);
        System.out.println(tagDao.findAll());
        System.out.println(tagDao.findById(1));
        System.out.println(tagDao.findByName("name"));
        Tag tag = new Tag();
        tag.setId(2);
        tag.setName("k");
        tagDao.insert(tag);
//        DataSource tagDao=  appContext.getBean("dataSource", DataSource.class);

    }
}
