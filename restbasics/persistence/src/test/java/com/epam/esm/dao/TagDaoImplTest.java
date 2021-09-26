package com.epam.esm.dao;

import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.controller.entity.Tag;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TagDaoImplTest {
    private static EmbeddedDatabase database;
    private static TagDao tagDAO;

    @BeforeAll
    public static void setUp() {
        database = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScripts("app_db.sql")
                .build();
        tagDAO = new TagDaoImpl(new JdbcTemplate(database));
    }

    @Test
    public void getTagByIdTestPositive() {
        Tag expectedTag = new Tag();
        expectedTag.setId(1);
        expectedTag.setName("extreme");
        Tag actualTag = tagDAO.findById(1);
        assertEquals(expectedTag, actualTag);
    }

    @Test
    public void getTagByNameTestPositive() {
        Tag expectedTag = new Tag();
        expectedTag.setId(1);
        expectedTag.setName("extreme");
        Tag actualTag = tagDAO.findByName("extreme");
        assertEquals(expectedTag, actualTag);
    }

    @Test
    public void findAllTest() {
        List<Tag> list = tagDAO.findAll();
        System.out.println(list);
        assertNotNull(list);
    }

}
