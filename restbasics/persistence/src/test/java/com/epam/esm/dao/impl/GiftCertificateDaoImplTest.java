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

    @BeforeAll
    static void setUp() {

        giftCertificate1 = GiftCertificate.builder()
                .id(1)
                .name("a")
                .price(BigDecimal.ONE)
                .build();
        giftCertificate2 = GiftCertificate.builder()
                .id(2)
                .name("2")
                .description("123")
                .price(BigDecimal.ONE)
                .build();
        giftCertificate3 = GiftCertificate.builder()
                .id(3)
                .name("3")
                .price(BigDecimal.ONE)
                .build();
        giftCertificate4 = GiftCertificate.builder()
                .id(1)
                .name("4")
                .price(BigDecimal.ZERO)
                .build();
        giftCertificate5 = GiftCertificate.builder()
                .name("Cinema")
                .description("Very good and not expensive")
                .price(BigDecimal.valueOf(100.1))
                .duration(1)
                .build();
        giftCertificate6 = GiftCertificate.builder()
                .name("name")
                .description("description pf person")
                .build();
    }


    @Test
    void addCorrectDataShouldReturnTagTest() {
        Optional<GiftCertificate> giftCertificate = giftCertificateDao.add(giftCertificate1);
        assertNotNull(giftCertificate);
    }

    @Test
    void addCorrectDataShouldReturnThisGiftCertificateTest() {
        Optional<GiftCertificate> actual = giftCertificateDao.add(giftCertificate1);
        GiftCertificate expected = giftCertificate1;
        assertEquals(expected, actual.get());
    }

    @Test
    void findAllShouldReturnListOfTagsTest() {
        long expected = 7;
        giftCertificateDao.add(giftCertificate1);
        giftCertificateDao.add(giftCertificate2);
        giftCertificateDao.add(giftCertificate3);
        List<GiftCertificate> actual = giftCertificateDao.findAll();
        assertEquals(expected, actual.size());
    }

    @ParameterizedTest
    @ValueSource(longs = {100, 213, Long.MAX_VALUE, Long.MIN_VALUE})
    void findByIdCorrectDataShouldReturnEmptyOptionalTest(long id) {
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

    @Test
    void findByNameCorrectDataShouldReturnEmptyOptionalTest() {
        String name = "121";
        Optional<GiftCertificate> actual = giftCertificateDao.findByName(name);
        assertFalse(actual.isPresent());
    }

    @Test
    void findByNameCorrectDataShouldReturnOptionalGiftCertificateTest() {
        String name = "a";
        giftCertificateDao.add(giftCertificate1);
        Optional<GiftCertificate> expected = Optional.of(giftCertificate1);
        Optional<GiftCertificate> actual = giftCertificateDao.findByName(name);
        assertEquals(expected, actual);
    }


    @ParameterizedTest
    @ValueSource(longs = {100, 213, Long.MAX_VALUE, Long.MIN_VALUE})
    void deleteCorrectDataShouldNotThrowExceptionTest(long id) {
        assertDoesNotThrow(() -> giftCertificateDao.delete(id));
    }


    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4, 5, 100, 213, Long.MAX_VALUE, Long.MIN_VALUE})
    void deleteCertificateCorrectDataShouldNotThrowExceptionTest() {
        giftCertificateDao.add(giftCertificate1);
        long id = 1;
        assertDoesNotThrow(() -> giftCertificateDao.delete(id));
    }

    @Test
    void addDeleteCertificateCorrectDataTest() {
        giftCertificateDao.add(giftCertificate1);
        giftCertificateDao.delete(1);
        giftCertificateDao.delete(2);
        giftCertificateDao.delete(3);
        giftCertificateDao.delete(4);
        giftCertificateDao.delete(5);
        List<GiftCertificate> expected = new ArrayList<>();
        List<GiftCertificate> actual = giftCertificateDao.findAll();
        assertEquals(expected, actual);
    }

    @Test
    void updateCorrectDataShouldNotThrowExceptionTest() {
        Optional<GiftCertificate> expected = Optional.of(giftCertificate4);
        giftCertificateDao.add(giftCertificate1);
        Optional<GiftCertificate> actual = giftCertificateDao.update(giftCertificate4);
        assertEquals(expected, actual);
    }

    @Test
    void updateCertificateCorrectDataShouldNotThrowExceptionTest() {
        assertDoesNotThrow(() -> giftCertificateDao.update(giftCertificate1));
    }




}

