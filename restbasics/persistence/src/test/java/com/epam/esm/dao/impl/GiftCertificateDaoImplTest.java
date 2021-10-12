package com.epam.esm.dao.impl;

import com.epam.esm.config.TestDatabaseConfig;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.GiftCertificate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
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

    private static GiftCertificate giftCertificate1;
    private static GiftCertificate giftCertificate2;
    private static GiftCertificate giftCertificate3;
    private static GiftCertificate giftCertificate4;
    private static GiftCertificate giftCertificate5;
    private static GiftCertificate giftCertificate6;

    @Autowired
    public GiftCertificateDaoImplTest(GiftCertificateDao giftCertificateDao) {
        this.giftCertificateDao = giftCertificateDao;
    }

    public static Object[][] sumTestData() {
        return new Object[][]{
                {new GiftCertificate(1, "a", BigDecimal.ONE,ZonedDateTime.now(),ZonedDateTime.now()},
                {10, 1, 11},
                {1000000, -1000000, 0}
        };
    }

    @BeforeAll
    static void setUp() {

        giftCertificate1 = GiftCertificate.builder()
                .id(1)
                .name("a")
                .price(BigDecimal.ONE)
                .createDate(ZonedDateTime.now())
                .lastUpdateDate(ZonedDateTime.now())
                .build();
        giftCertificate2 = GiftCertificate.builder()
                .id(2)
                .name("2")
                .description("123")
                .price(BigDecimal.ONE)
                .createDate(ZonedDateTime.now())
                .lastUpdateDate(ZonedDateTime.now())
                .build();
        giftCertificate3 = GiftCertificate.builder()
                .id(3)
                .name("3")
                .price(BigDecimal.ONE)
                .createDate(ZonedDateTime.now())
                .lastUpdateDate(ZonedDateTime.now())
                .build();
        giftCertificate4 = GiftCertificate.builder()
                .id(1)
                .name("4")
                .price(BigDecimal.ZERO)
                .createDate(ZonedDateTime.now())
                .lastUpdateDate(ZonedDateTime.now())
                .build();
        giftCertificate5 = GiftCertificate.builder()
                .name("Cinema")
                .description("Very good and not expensive")
                .price(BigDecimal.valueOf(100.1))
                .duration(1)
                .createDate(ZonedDateTime.now())
                .lastUpdateDate(ZonedDateTime.now())
                .build();
        giftCertificate6 = GiftCertificate.builder()
                .name("name")
                .description("description pf person")
                .createDate(ZonedDateTime.now())
                .lastUpdateDate(ZonedDateTime.now())
                .build();
    }


    @Test
    void addCorrectDataShouldReturnTagTest() {
        GiftCertificate giftCertificate = giftCertificateDao.create(giftCertificate1);
        assertNotNull(giftCertificate);
    }

    @Test
    void addCorrectDataShouldReturnThisGiftCertificateTest() {
       GiftCertificate actual = giftCertificateDao.create(giftCertificate1);
        GiftCertificate expected = giftCertificate1;
        assertEquals(expected, actual);
    }

    @Test
    void findAllShouldReturnListOfTagsTest() {
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
        //todo should return empty result when find by id
        Optional<GiftCertificate> actual = giftCertificateDao.findById(id);
        assertFalse(actual.isPresent());
    }

    @Test
    void findByIdCorrectDataShouldReturnGiftCertificateTest() {
        String cinema = "Cinema";
        long id = 1;
        Optional<GiftCertificate> actual = giftCertificateDao.findById(id);
        assertEquals(cinema, actual.get().getName());
    }

//    @Test
//    void findByNameCorrectDataShouldReturnEmptyOptionalTest() {
//        String name = "121";
//        Optional<GiftCertificate> actual = giftCertificateDao.findBy(name);
//        assertFalse(actual.isPresent());
//    }

//    @Test
//    void findByNameCorrectDataShouldReturnOptionalGiftCertificateTest() {
//        String name = "a";
//        giftCertificateDao.create(giftCertificate1);
//        Optional<GiftCertificate> expected = Optional.of(giftCertificate1);
//        Optional<GiftCertificate> actual = giftCertificateDao.findByName(name);
//        assertEquals(expected, actual);
//    }


    @ParameterizedTest
    @ValueSource(longs = {100, 213, Long.MAX_VALUE, Long.MIN_VALUE})
    void deleteCorrectDataShouldNotThrowExceptionTest(long id) {
        assertDoesNotThrow(() -> giftCertificateDao.delete(id));
    }


    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4, 5, 100, 213, Long.MAX_VALUE, Long.MIN_VALUE})
    void deleteCertificateCorrectDataShouldNotThrowExceptionTest() {
        giftCertificateDao.create(giftCertificate1);
        long id = 1;
        assertDoesNotThrow(() -> giftCertificateDao.delete(id));
    }

    @Test
    void addDeleteCertificateCorrectDataTest() {
        giftCertificateDao.create(giftCertificate1);
        giftCertificateDao.delete(1L);
        giftCertificateDao.delete(2L);
        giftCertificateDao.delete(3L);
        giftCertificateDao.delete(4L);
        giftCertificateDao.delete(5L);
        List<GiftCertificate> expected = new ArrayList<>();
        List<GiftCertificate> actual = giftCertificateDao.findAll();
        assertEquals(expected, actual);
    }

    @Test
    void updateCorrectDataShouldNotThrowExceptionTest() {
        GiftCertificate expected = giftCertificate4;
        giftCertificateDao.create(giftCertificate1);
        GiftCertificate actual = giftCertificateDao.update(giftCertificate4);
        assertEquals(expected, actual);
    }

    @Test
    void updateCertificateCorrectDataShouldNotThrowExceptionTest() {
        assertDoesNotThrow(() -> giftCertificateDao.update(giftCertificate1));
    }




}

