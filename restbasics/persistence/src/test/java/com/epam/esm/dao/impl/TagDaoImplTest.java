//package com.epam.esm.dao.impl;
//
//import com.epam.esm.config.TestDatabaseConfig;
//import com.epam.esm.dao.TagDao;
//import com.epam.esm.entity.Tag;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.ValueSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//import static org.mockito.Mockito.doNothing;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = TestDatabaseConfig.class)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
//@ActiveProfiles("dev")
//class TagDaoImplTest {
//
//    private final TagDao tagDao;
//    private static Tag tag1;
//    private static Tag tag2;
//    private static Tag tag3;
//
//
//    @BeforeAll
//    static void setUp() {
//
//        tag1 = Tag.builder()
//                .id(1)
//                .name("1")
//                .build();
//        tag2 = Tag.builder()
//                .id(2)
//                .name("2")
//                .build();
//        tag3 = Tag.builder()
//                .id(3)
//                .name("3")
//                .build();
//    }
//
//
//    @Autowired
//    public TagDaoImplTest(TagDao tagDao) {
//        this.tagDao = tagDao;
//    }
//
//    @Test
//    void insertCorrectDataShouldReturnTagTest() {
//        Optional<Tag> actual = tagDao.insert(tag1);
//        assertNotNull(actual);
//    }
//
//    @Test
//    void insertCorrectDataShouldSetIdTest() {
//        long expected = 5;
//        tagDao.insert(tag2);
//        long actual = tag2.getId();
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    void findAllShouldReturnListOfTagsTest() {
//        long expected = 7;
//        tagDao.insert(tag1);
//        tagDao.insert(tag2);
//        tagDao.insert(tag3);
//        List<Tag> actual = tagDao.findAll();
//        assertEquals(expected, actual.size());
//    }
//
//    @Test
//    void findByIdCorrectDataShouldReturnTagOptionalTest() {
//        long id = 5;
//        tagDao.insert(tag3);
//        Optional<Tag> actual = tagDao.findById(id);
//        assertEquals(Optional.of(tag3), actual);
//    }
//
//    @ParameterizedTest
//    @ValueSource(longs = {100, 213, Long.MAX_VALUE, Long.MIN_VALUE})
//    void findByIdCorrectDataShouldReturnEmptyOptionalTest(long id) {
//        Optional<Tag> actual = tagDao.findById(id);
//        assertFalse(actual.isPresent());
//    }
//
//
//    @ParameterizedTest
//    @ValueSource(longs = {100, 213, Long.MAX_VALUE, Long.MIN_VALUE})
//    void removeCorrectDataShouldNotThrowExceptionTest(long id) {
//        assertDoesNotThrow(() -> tagDao.delete(id));
//    }
//
//
//    @Test
//    void findByGiftCertificateIdInCorrectDataShouldReturnTest() {
//        long giftCertificateId = 2;
//        int expected = 2;
//        tagDao.insert(tag1);
//        tagDao.insert(tag2);
//        List<Tag> actualList = tagDao.findByGiftCertificateId(giftCertificateId);
//        int actual = actualList.size();
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    void findByGiftCertificateIdCorrectDataShouldReturnEmptyListTest() {
//        long giftCertificateId = 5;
//        List<Tag> actual = tagDao.findByGiftCertificateId(giftCertificateId);
//        assertTrue(actual.isEmpty());
//    }
//
//    @Test
//    void findByNameCorrectDataShouldReturnTagOptionalTest() {
//        String name = "2";
//        tagDao.insert(tag2);
//        Optional<Tag> actual = tagDao.findByName(name);
//        assertEquals(Optional.of(tag2).get().getName(), actual.get().getName());
//    }
//
//    @Test
//    void findByNameCorrectDataShouldReturnEmptyOptionalTest() {
//        String name = "Work";
//        Optional<Tag> actual = tagDao.findByName(name);
//        assertFalse(actual.isPresent());
//    }
//}