package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.dto.GiftCertificateInputDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.DublicateResourceException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateQueryParamDto;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.ServiceGiftCertificateMapper;
import com.epam.esm.mapper.ServiceTagMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CertificateServiceImplTest {

    private static GiftCertificateDao giftCertificateDao;
    private static ModelMapper modelMapper;
    private static ServiceTagMapper serviceTagMapper;
    private static GiftCertificateService giftCertificateService;
    private static TagService tagService;
    private static TagDao tagDao;

    @BeforeAll
    static void setUp() {
        giftCertificateDao = mock(GiftCertificateDaoImpl.class);
        tagService = mock(TagServiceImpl.class);
        tagDao = mock(TagDao.class);
        modelMapper = new ModelMapper();
        serviceTagMapper = new ServiceTagMapper(modelMapper);
        ServiceGiftCertificateMapper serviceGiftCertificateMapper = new ServiceGiftCertificateMapper(modelMapper, serviceTagMapper);
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
        giftCertificateService = spy(new GiftCertificateServiceImpl(giftCertificateDao, tagDao, serviceGiftCertificateMapper, serviceTagMapper));
    }

    @AfterAll
    static void tearDown() {
        giftCertificateDao = null;
        modelMapper = null;
        giftCertificateService = null;
        tagService = null;
    }

    public static Object[][] createGiftCertificatesAndDto() {
        var zonedDateTime = ZonedDateTime.now(ZoneId.systemDefault());
        return new Object[][]{
                {
                        new GiftCertificate(1, "a", "b", BigDecimal.ONE,zonedDateTime, zonedDateTime, 0, new HashSet<>()),
                        new GiftCertificateDto(1, "a", "b", BigDecimal.ONE, 0, new HashSet<>(), zonedDateTime, zonedDateTime)},
                {
                        new GiftCertificate(1, "2", "123", BigDecimal.ONE, zonedDateTime, zonedDateTime, 0, new HashSet<>()),
                        new GiftCertificateDto(1, "2", "123", BigDecimal.ONE, 0, new HashSet<>(), zonedDateTime, zonedDateTime)}
        };
    }

    public static Object[][] createGiftCertificatesAndInputDto() {
        var zonedDateTime = ZonedDateTime.now(ZoneId.systemDefault());
        return new Object[][]{
                {
                        new GiftCertificate(1, "a", "b", BigDecimal.ONE, zonedDateTime, zonedDateTime, 0, new HashSet<>()),
                        new GiftCertificateInputDto("a", "b", BigDecimal.ONE, 0),
                        new GiftCertificateDto(1, "a", "b", BigDecimal.ONE, 0, new HashSet<>(), zonedDateTime, zonedDateTime)},

                {
                        new GiftCertificate(1, "2", "123", BigDecimal.ONE, zonedDateTime, zonedDateTime, 0, new HashSet<>()),
                        new GiftCertificateInputDto("2", "123", BigDecimal.ONE, 0),
                        new GiftCertificateDto(1, "2", "123", BigDecimal.ONE, 0, new HashSet<>(), zonedDateTime, zonedDateTime)},
        };
    }

    public static Object[][] createGiftCertificatesAndInputDtoForUpdate() {
        var zonedDateTime = ZonedDateTime.now(ZoneId.systemDefault());
        return new Object[][]{
                {
                        new GiftCertificate(1, "ad", "bs", BigDecimal.ONE, zonedDateTime, zonedDateTime, 0, new HashSet<>()),
                        new GiftCertificate(1, "a", "b", BigDecimal.ONE, zonedDateTime, zonedDateTime, 0, new HashSet<>()),
                        new GiftCertificateInputDto("a", "b", BigDecimal.ONE, 0),
                        new GiftCertificateDto(1, "a", "b", BigDecimal.ONE, 0, new HashSet<>(), zonedDateTime, zonedDateTime)},

                {
                        new GiftCertificate(1, "ad", "bs", BigDecimal.ONE, zonedDateTime, zonedDateTime, 0, new HashSet<>()),
                        new GiftCertificate(1, "2", "123", BigDecimal.ONE, zonedDateTime, zonedDateTime, 0, new HashSet<>()),
                        new GiftCertificateInputDto("2", "123", BigDecimal.ONE, 0),
                        new GiftCertificateDto(1, "2", "123", BigDecimal.ONE, 0, new HashSet<>(), zonedDateTime, zonedDateTime)},
        };
    }

    public static Object[][] createGiftCertificatesTogether() {
        var zonedDateTime = ZonedDateTime.now(ZoneId.systemDefault());
        return new Object[][]{
                {new GiftCertificate(1, "a", "b", BigDecimal.ONE, zonedDateTime, zonedDateTime, 0, new HashSet<>()),
                        new GiftCertificate(1, "2", "123", BigDecimal.ONE, zonedDateTime, zonedDateTime, 0, new HashSet<>()),
                        new GiftCertificateQueryParamDto()
                }
        };
    }

    @ParameterizedTest
    @MethodSource("createGiftCertificatesAndInputDto")
    void shouldReturnGiftCertificateDtoWhenAddGiftCertificateTest(GiftCertificate certificate, GiftCertificateInputDto certificateDto, GiftCertificateDto giftCertificateDto) {
        when(giftCertificateDao.create(any(GiftCertificate.class))).thenReturn(certificate);
        when(giftCertificateDao.findByName(any())).thenReturn(Optional.empty());
        GiftCertificateDto actual = giftCertificateService.create(certificateDto);
        assertEquals(giftCertificateDto, actual);
    }

    @ParameterizedTest
    @MethodSource("createGiftCertificatesAndInputDto")
    void shouldThrowExceptionWhenAddGiftCertificateTest(GiftCertificate certificate, GiftCertificateInputDto certificateDto) {
        when(giftCertificateDao.findByName(any(String.class))).thenReturn(Optional.of(certificate));
        when(giftCertificateDao.create(any(GiftCertificate.class))).thenReturn(certificate);
        assertThrows(DublicateResourceException.class,
                () -> giftCertificateService.create(certificateDto));
    }

    @ParameterizedTest
    @MethodSource("createGiftCertificatesTogether")
    void shouldReturnListOfGiftCertificateDtoWhenFindGiftCertificateTest(GiftCertificate giftCertificate1, GiftCertificate giftCertificate2, GiftCertificateQueryParamDto param) {
        int expectedSize = 2;
        when(giftCertificateDao.findByQueryParameters(any(String.class)))
                .thenReturn(Arrays.asList(giftCertificate1, giftCertificate2));
        when(tagService.findTagsByGiftCertificateId(any(long.class))).thenReturn(new HashSet<>());
        List<GiftCertificateDto> actual = giftCertificateService.findGiftCertificates(param);
        assertEquals(expectedSize, actual.size());
    }

    @ParameterizedTest
    @MethodSource("createGiftCertificatesAndDto")
    void shouldReturnGiftCertificateDtoWhenFindGiftCertificateByIdTest(GiftCertificate certificate, GiftCertificateDto certificateDto) {
        long id = 1;
        when(giftCertificateDao.findById(any(long.class))).thenReturn(Optional.of(certificate));
        when(tagService.findTagsByGiftCertificateId(any(long.class))).thenReturn(new HashSet<>());
        GiftCertificateDto actual = giftCertificateService.findById(id);
        assertEquals(certificateDto, actual);
    }

    @ParameterizedTest
    @ValueSource(longs = {5, 213, Long.MAX_VALUE})
    void shouldThrowExceptionWhenFindGiftCertificateByIdTest(Long id) {
        when(giftCertificateDao.findById(any(long.class))).thenReturn(Optional.empty());
        when(tagService.findTagsByGiftCertificateId(any(long.class))).thenReturn(new HashSet<>());
        assertThrows(ResourceNotFoundException.class, () -> giftCertificateService.findById(id));
    }

    @ParameterizedTest
    @MethodSource("createGiftCertificatesAndInputDtoForUpdate")
    void shouldReturnGiftCertificateDtoWhenUpdateGiftCertificateTest
            (GiftCertificate certificate0, GiftCertificate certificate, GiftCertificateInputDto certificateInputDto, GiftCertificateDto certificateDto) {
        when(giftCertificateDao.findById(any(long.class))).thenReturn(Optional.of(certificate0));
        when(giftCertificateDao.update(any(GiftCertificate.class))).thenReturn(certificate);
        GiftCertificateDto actual = giftCertificateService.update(certificate0.getId(), certificateInputDto);
        assertEquals(certificateDto, actual);
    }


    @ParameterizedTest
    @MethodSource("createGiftCertificatesAndInputDto")
    void shouldThrowExceptionWhenUpdateGiftCertificateIncorrectDataTest(GiftCertificate certificate, GiftCertificateInputDto certificateDto) {
        when(giftCertificateDao.findById(any(long.class))).thenReturn(Optional.empty());
        when(giftCertificateDao.update(any(GiftCertificate.class))).thenReturn(certificate);
        assertThrows(ResourceNotFoundException.class,
                () -> giftCertificateService.update(certificate.getId(), certificateDto));
    }

    @ParameterizedTest
    @MethodSource("createGiftCertificatesAndDto")
    void shouldNotThrowExceptionWhenRemoveGiftCertificateTest(GiftCertificate certificate) {
        long id = 1;
        doNothing().when(giftCertificateDao).delete(any(long.class));
        when(giftCertificateDao.findById(any(long.class))).thenReturn(Optional.of(certificate));
        assertDoesNotThrow(() -> giftCertificateService.delete(id));
    }

}