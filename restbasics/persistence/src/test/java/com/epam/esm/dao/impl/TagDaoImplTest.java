package com.epam.esm.dao.impl;

import com.epam.esm.config.TestDatabaseConfig;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestDatabaseConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("dev")
class TagDaoImplTest {

    private final TagDao tagDao;

    @Autowired
    public TagDaoImplTest(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    public static Object[][] createTags() {
        return new Object[][]{
                {new Tag(5, "1")},
                {new Tag(6, "2")},
                {new Tag(7, "3")}
        };
    }

    public static Object[][] createTagsTogether() {
        return new Object[][]{
                {new Tag(5, "1"), new Tag(6, "2"), new Tag(7, "3")},
                {new Tag(512412421, ""), new Tag(61, "2 das"), new Tag(7, "3")},
                {new Tag(5, "1"), new Tag(123, "dsa ds2"), new Tag(132321237, "3ccz")}
        };
    }


    @ParameterizedTest
    @MethodSource("createTags")
    void shouldReturnTagTest(Tag tag) {
        Tag actual = tagDao.create(tag);
        assertNotNull(actual);
    }

    @ParameterizedTest
    @MethodSource("createTags")
    void shouldSetIdWhenCreateTest(Tag tag) {
        long expected = 5;
        Tag actualTag = tagDao.create(tag);
        long actual = actualTag.getId();
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("createTagsTogether")
    void shouldReturnListOfTagsWhenFindAllTest(Tag tag1, Tag tag2, Tag tag3) {
        long expected = 7;
        tagDao.create(tag1);
        tagDao.create(tag2);
        tagDao.create(tag3);
        List<Tag> actual = tagDao.findAll();
        assertEquals(expected, actual.size());
    }

    @ParameterizedTest
    @MethodSource("createTags")
    void shouldReturnTagOptionalWhenFindByIdTest(Tag tag) {
        long id = 5;
        tagDao.create(tag);
        Optional<Tag> actual = tagDao.findById(id);
        assertEquals(Optional.of(tag), actual);
    }

    @ParameterizedTest
    @ValueSource(longs = {100, 213, Long.MAX_VALUE, Long.MIN_VALUE})
    void shouldReturnEmptyOptionalWhenFindByIdTest(long id) {
        Optional<Tag> actual = tagDao.findById(id);
        assertFalse(actual.isPresent());
    }


    @ParameterizedTest
    @ValueSource(longs = {100, 213, Long.MAX_VALUE, Long.MIN_VALUE})
    void shouldNotThrowExceptionWhenRemoveCorrectDataTest(long id) {
        assertDoesNotThrow(() -> tagDao.delete(id));
    }

    @ParameterizedTest
    @MethodSource("createTagsTogether")
    void shouldListOfTagsWhenFindByCertificateIdTest(Tag tag1, Tag tag2) {
        long giftCertificateId = 2;
        int expected = 2;
        tagDao.create(tag1);
        tagDao.create(tag2);
        List<Tag> actualList = tagDao.findByCertificateId(giftCertificateId);
        int actual = actualList.size();
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(longs = {123, 124214214, Long.MAX_VALUE})
    void shouldReturnEmptyListWhenFindByCertificateIdTest(Long id) {
        List<Tag> actual = tagDao.findByCertificateId(id);
        assertTrue(actual.isEmpty());
    }

    @ParameterizedTest
    @MethodSource("createTags")
    void findByNameCorrectDataShouldReturnTagOptionalTest(Tag expected) {
        String name = expected.getName();
        tagDao.create(expected);
        Optional<Tag> actual = tagDao.findByName(name);
        assertEquals(Optional.of(expected), actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {"WORK", "SDAAS", "    1"})
    void findByNameCorrectDataShouldReturnEmptyOptionalTest(String name) {
        Optional<Tag> actual = tagDao.findByName(name);
        assertFalse(actual.isPresent());
    }
}