package com.epam.esm.dao.impl;

import com.epam.esm.config.TestDatabaseConfig;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestDatabaseConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("dev")
class GiftCertificateDaoImplTest {

    private final GiftCertificateDao giftCertificateDao;


    @Autowired
    public GiftCertificateDaoImplTest(GiftCertificateDao giftCertificateDao) {
        this.giftCertificateDao = giftCertificateDao;
    }

    public static Object[][] createGiftCertificates() {
        return new Object[][]{
                {new GiftCertificate(1, "a", "b", BigDecimal.ONE, ZonedDateTime.now(), ZonedDateTime.now(), 0, new HashSet<>())},
                {new GiftCertificate(1, "2", "123", BigDecimal.ONE, ZonedDateTime.now(), ZonedDateTime.now(), 0, new HashSet<>())},
                {new GiftCertificate(1, "3", "wow", BigDecimal.ONE, ZonedDateTime.now(), ZonedDateTime.now(), 0, new HashSet<>())}
        };
    }

    public static Object[][] createGiftCertificatesTogether() {
        return new Object[][]{
                {new GiftCertificate(1, "a", "b", BigDecimal.ONE, ZonedDateTime.now(), ZonedDateTime.now(), 0, new HashSet<>()),
                        new GiftCertificate(1, "2", "123", BigDecimal.ONE, ZonedDateTime.now(), ZonedDateTime.now(), 0, new HashSet<>()),
                        new GiftCertificate(1, "3", "wow", BigDecimal.ONE, ZonedDateTime.now(), ZonedDateTime.now(), 0, new HashSet<>())
                }};
    }

    @ParameterizedTest
    @MethodSource("createGiftCertificates")
    void shouldReturnTagWhenCreateTest(GiftCertificate certificate) {
        GiftCertificate giftCertificate = giftCertificateDao.create(certificate);
        assertNotNull(giftCertificate);
    }

    @ParameterizedTest
    @MethodSource("createGiftCertificates")
    void shouldReturnThisGiftCertificateWhenAddTest(GiftCertificate certificate) {
        GiftCertificate actual = giftCertificateDao.create(certificate);
        GiftCertificate expected = certificate;
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("createGiftCertificatesTogether")
    void shouldReturnListOfTagsWhenFindAllTest(GiftCertificate giftCertificate1,GiftCertificate giftCertificate2, GiftCertificate giftCertificate3) {
        long expected = 7;
        giftCertificateDao.create(giftCertificate1);
        giftCertificateDao.create(giftCertificate2);
        giftCertificateDao.create(giftCertificate3);
        List<GiftCertificate> actual = giftCertificateDao.findAll();
        assertEquals(expected, actual.size());
    }

    @ParameterizedTest
    @ValueSource(longs = {100, 213, Long.MAX_VALUE, Long.MIN_VALUE})
    void findByIdCorrectDataShouldReturnEmptyOptionalTest(long id) {

        Optional<GiftCertificate> actual = giftCertificateDao.findById(id);
        assertFalse(actual.isPresent());
    }

//    @Test
//    void findByIdCorrectDataShouldReturnGiftCertificateTest() {
//        String cinema = "Cinema";
//        long id = 1;
//        Optional<GiftCertificate> actual = giftCertificateDao.findById(id);
//        assertEquals(cinema, actual.get().getName());
//    }
//
////    @Test
////    void findByNameCorrectDataShouldReturnEmptyOptionalTest() {
////        String name = "121";
////        Optional<GiftCertificate> actual = giftCertificateDao.findBy(name);
////        assertFalse(actual.isPresent());
////    }
//
////    @Test
////    void findByNameCorrectDataShouldReturnOptionalGiftCertificateTest() {
////        String name = "a";
////        giftCertificateDao.create(giftCertificate1);
////        Optional<GiftCertificate> expected = Optional.of(giftCertificate1);
////        Optional<GiftCertificate> actual = giftCertificateDao.findByName(name);
////        assertEquals(expected, actual);
////    }
//
//
//    @ParameterizedTest
//    @ValueSource(longs = {100, 213, Long.MAX_VALUE, Long.MIN_VALUE})
//    void deleteCorrectDataShouldNotThrowExceptionTest(long id) {
//        assertDoesNotThrow(() -> giftCertificateDao.delete(id));
//    }
//
//
//    @ParameterizedTest
//    @ValueSource(longs = {1, 2, 3, 4, 5, 100, 213, Long.MAX_VALUE, Long.MIN_VALUE})
//    void deleteCertificateCorrectDataShouldNotThrowExceptionTest() {
//        giftCertificateDao.create(giftCertificate1);
//        long id = 1;
//        assertDoesNotThrow(() -> giftCertificateDao.delete(id));
//    }
//
//    @Test
//    void addDeleteCertificateCorrectDataTest() {
//        giftCertificateDao.create(giftCertificate1);
//        giftCertificateDao.delete(1L);
//        giftCertificateDao.delete(2L);
//        giftCertificateDao.delete(3L);
//        giftCertificateDao.delete(4L);
//        giftCertificateDao.delete(5L);
//        List<GiftCertificate> expected = new ArrayList<>();
//        List<GiftCertificate> actual = giftCertificateDao.findAll();
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    void updateCorrectDataShouldNotThrowExceptionTest() {
//        GiftCertificate expected = giftCertificate4;
//        giftCertificateDao.create(giftCertificate1);
//        GiftCertificate actual = giftCertificateDao.update(giftCertificate4);
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    void updateCertificateCorrectDataShouldNotThrowExceptionTest() {
//        assertDoesNotThrow(() -> giftCertificateDao.update(giftCertificate1));
//    }


}

