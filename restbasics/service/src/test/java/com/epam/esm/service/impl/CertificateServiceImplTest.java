//package com.epam.esm.service.impl;
//
//import com.epam.esm.dao.GiftCertificateDao;
//import com.epam.esm.dao.TagDao;
//import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
//import com.epam.esm.entity.GiftCertificate;
//import com.epam.esm.entity.SortType;
//import com.epam.esm.entity.OrderType;
//import com.epam.esm.service.GiftCertificateService;
//import com.epam.esm.service.TagService;
//import com.epam.esm.dto.GiftCertificateDto;
//import com.epam.esm.dto.GiftCertificateQueryParamDto;
//import com.epam.esm.exception.ResourceNotFoundException;
//import com.epam.esm.mapper.ServiceGiftCertificateMapper;
//import com.epam.esm.mapper.ServiceTagMapper;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.modelmapper.ModelMapper;
//import org.modelmapper.convention.MatchingStrategies;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.time.ZoneId;
//import java.time.ZonedDateTime;
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//import static org.mockito.Mockito.doNothing;
//
//class CertificateServiceImplTest {
//
//    private static GiftCertificateDao giftCertificateDao;
//    private static ModelMapper modelMapper;
//    private static ServiceTagMapper serviceTagMapper;
//    private static GiftCertificateService giftCertificateService;
//    private static TagService tagService;
//    private static TagDao tagDao;
//    private static GiftCertificate giftCertificate1;
//    private static GiftCertificate giftCertificate2;
//    private static GiftCertificateDto giftCertificateDto1;
//    private static GiftCertificateDto giftCertificateDto2;
//    private static GiftCertificateDto giftCertificateDto3;
//    private static GiftCertificateDto giftCertificateDto4;
//    private static GiftCertificateDto giftCertificateDto5;
//    private static GiftCertificateDto giftCertificateDto6;
//    private static GiftCertificateDto giftCertificateDto7;
//    private static GiftCertificateQueryParamDto giftCertificateQueryParametersDto1;
//
//    @BeforeAll
//    static void setUp() {
//        giftCertificateDao = mock(GiftCertificateDaoImpl.class);
//        tagService = mock(TagServiceImpl.class);
//        tagDao = mock(TagDao.class);
//        modelMapper = new ModelMapper();
//        serviceTagMapper = new ServiceTagMapper(modelMapper);
//        ServiceGiftCertificateMapper serviceGiftCertificateMapper = new ServiceGiftCertificateMapper(modelMapper, serviceTagMapper);
//        modelMapper.getConfiguration()
//                .setMatchingStrategy(MatchingStrategies.STRICT)
//                .setFieldMatchingEnabled(true)
//                .setSkipNullEnabled(true)
//                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
//        giftCertificateService = new GiftCertificateServiceImpl(giftCertificateDao, tagDao, serviceGiftCertificateMapper, serviceTagMapper);
//        giftCertificate1 = GiftCertificate.builder()
//                .id(1L)
//                .name("Cinema")
//                .description("Best cinema in the city")
//                .price(new BigDecimal(100))
//                .duration(5)
//                .createDate(ZonedDateTime.of(LocalDateTime.of(2020, 12, 12, 12, 0, 0), ZoneId.systemDefault()))
//                .lastUpdateDate(ZonedDateTime.of(LocalDateTime.of(2020, 12, 13, 12, 0, 0), ZoneId.systemDefault()))
//                .tags(new HashSet<>())
//                .build();
//        giftCertificate2 = GiftCertificate.builder()
//                .id(1L)
//                .name("Travel to German")
//                .description("You will like it")
//                .price(new BigDecimal(500))
//                .duration(1)
//                .createDate(ZonedDateTime.of(LocalDateTime.of(2020, 12, 12, 12, 0, 0), ZoneId.systemDefault()))
//                .lastUpdateDate(ZonedDateTime.of(LocalDateTime.of(2020, 12, 13, 12, 0, 0), ZoneId.systemDefault()))
//                .tags(new HashSet<>())
//                .build();
//        giftCertificateDto1 = GiftCertificateDto.builder()
//                .id(1)
//                .name("Cinema")
//                .description("Best cinema in the city")
//                .price(new BigDecimal(100))
//                .duration(5)
//                .createDate(ZonedDateTime.of(LocalDateTime.of(2020, 12, 12, 12, 0, 0), ZoneId.systemDefault()))
//                .lastUpdateDate(ZonedDateTime.of(LocalDateTime.of(2020, 12, 13, 12, 0, 0), ZoneId.systemDefault()))
//                .tags(new HashSet<>())
//                .build();
//        giftCertificateDto2 = GiftCertificateDto.builder()
//                .id(1L)
//                .name("Cinema")
//                .description("Best cinema in the city")
//                .price(new BigDecimal(100))
//                .duration(5)
//                .createDate(ZonedDateTime.of(LocalDateTime.of(2020, 12, 12, 12, 0, 0), ZoneId.systemDefault()))
//                .lastUpdateDate(ZonedDateTime.of(LocalDateTime.of(2020, 12, 13, 12, 0, 0), ZoneId.systemDefault()))
//                .tags(new HashSet<>())
//                .build();
//        giftCertificateDto3 = GiftCertificateDto.builder()
//                .name("Cinema")
//                .description("You will like it")
//                .price(new BigDecimal(100))
//                .duration(-5)
//                .createDate(ZonedDateTime.of(LocalDateTime.of(2020, 12, 12, 12, 0, 0), ZoneId.systemDefault()))
//                .lastUpdateDate(ZonedDateTime.of(LocalDateTime.of(2020, 12, 13, 12, 0, 0), ZoneId.systemDefault()))
//                .build();
//        giftCertificateDto4 = GiftCertificateDto.builder()
//                .id(1L)
//                .name("Travel to German")
//                .description("You will like it")
//                .price(new BigDecimal(500))
//                .duration(1)
//                .createDate(ZonedDateTime.of(LocalDateTime.of(2020, 12, 12, 12, 0, 0), ZoneId.systemDefault()))
//                .lastUpdateDate(ZonedDateTime.of(LocalDateTime.of(2020, 12, 13, 12, 0, 0), ZoneId.systemDefault()))
//                .tags(new HashSet<>())
//                .build();
//        giftCertificateDto5 = GiftCertificateDto.builder()
//                .id(1L)
//                .name("Cinema")
//                .duration(5)
//                .createDate(ZonedDateTime.of(LocalDateTime.of(2020, 12, 12, 12, 0, 0), ZoneId.systemDefault()))
//                .lastUpdateDate(ZonedDateTime.of(LocalDateTime.of(2020, 12, 13, 12, 0, 0), ZoneId.systemDefault()))
//                .build();
//        giftCertificateDto6 = GiftCertificateDto.builder()
//                .id(1L)
//                .name("Best cinema in the city")
//                .duration(5)
//                .createDate(ZonedDateTime.of(LocalDateTime.of(2020, 12, 12, 12, 0, 0), ZoneId.systemDefault()))
//                .lastUpdateDate(ZonedDateTime.of(LocalDateTime.of(2020, 12, 13, 12, 0, 0), ZoneId.systemDefault()))
//                .build();
//        giftCertificateDto7 = GiftCertificateDto.builder()
//                .id(1L)
//                .name("Cinema")
//                .duration(5)
//                .description("Nice")
//                .price(new BigDecimal("-1"))
//                .build();
//        giftCertificateQueryParametersDto1 = GiftCertificateQueryParamDto.builder()
//                .tagName("Sport")
//                .name("o")
//                .description("i")
//                .sortType(OrderType.NAME)
//                .orderType(SortType.DESC)
//                .build();
//    }
//
//    @AfterAll
//    static void tearDown() {
//        giftCertificateDao = null;
//        modelMapper = null;
//        giftCertificateService = null;
//        tagService = null;
//        giftCertificate1 = null;
//        giftCertificate2 = null;
//        giftCertificateDto1 = null;
//        giftCertificateDto2 = null;
//        giftCertificateDto3 = null;
//        giftCertificateDto4 = null;
//        giftCertificateDto5 = null;
//        giftCertificateDto6 = null;
//        giftCertificateDto7 = null;
//        giftCertificateQueryParametersDto1 = null;
//    }
//
//    @Test
//    void addGiftCertificateCorrectDataShouldReturnGiftCertificateDtoTest() {
//        when(giftCertificateDao.add(any(GiftCertificate.class))).thenReturn(Optional.of(giftCertificate1));
//        GiftCertificateDto actual = giftCertificateService.create(giftCertificateDto1);
//        assertEquals(giftCertificateDto2, actual);
//    }
//
//    @Test
//    void addGiftCertificateIncorrectDataShouldThrowExceptionTest() {
//        when(giftCertificateDao.findByName(any(String.class))).thenReturn(Optional.empty());
//        when(giftCertificateDao.add(any(GiftCertificate.class))).thenReturn(Optional.of(giftCertificate1));
//        assertThrows(ResourceNotFoundException.class,
//                () -> giftCertificateService.create(giftCertificateDto3));
//    }
//
//    @Test
//    void findGiftCertificatesCorrectDataShouldReturnListOfGiftCertificateDtoTest() {
//        int expectedSize = 2;
//        when(giftCertificateDao.findByQueryParameters(any(String.class)))
//                .thenReturn(Arrays.asList(giftCertificate1, giftCertificate2));
//        when(tagService.findTagsByGiftCertificateId(any(long.class))).thenReturn(new HashSet<>());
//        List<GiftCertificateDto> actual
//                = giftCertificateService.findGiftCertificates(giftCertificateQueryParametersDto1);
//        assertEquals(expectedSize, actual.size());
//    }
//
//    @Test
//    void findGiftCertificateByIdCorrectDataShouldReturnGiftCertificateDtoTest() {
//        long id = 1;
//        when(giftCertificateDao.findById(any(long.class))).thenReturn(Optional.of(giftCertificate2));
//        when(tagService.findTagsByGiftCertificateId(any(long.class))).thenReturn(new HashSet<>());
//        GiftCertificateDto actual = giftCertificateService.findById(id);
//        assertEquals(giftCertificateDto4, actual);
//    }
//
//    @Test
//    void findGiftCertificateByIdCorrectDataShouldThrowExceptionTest() {
//        long id = 5;
//        when(giftCertificateDao.findById(any(long.class))).thenReturn(Optional.empty());
//        when(tagService.findTagsByGiftCertificateId(any(long.class))).thenReturn(new HashSet<>());
//        assertThrows(ResourceNotFoundException.class, () -> giftCertificateService.findById(id));
//    }
//
//    @Test
//    void findGiftCertificateByIdIncorrectDataShouldThrowExceptionTest() {
//        long id = 1000;
//        when(giftCertificateDao.findById(any(long.class))).thenReturn(Optional.empty());
//        when(tagService.findTagsByGiftCertificateId(any(long.class))).thenReturn(new HashSet<>());
//        assertThrows(ResourceNotFoundException.class, () -> giftCertificateService.findById(id));
//    }
//
//    @Test
//    void updateGiftCertificateCorrectDataShouldReturnGiftCertificateDtoTest() {
//        when(giftCertificateDao.findById(any(long.class))).thenReturn(Optional.of(giftCertificate2));
//        when(giftCertificateDao.update(any(GiftCertificate.class))).thenReturn(Optional.of(giftCertificate1));
//        GiftCertificateDto actual = giftCertificateService.update(giftCertificateDto5);
//        assertEquals(giftCertificateDto2, actual);
//    }
//
//    @Test
//    void updateGiftCertificateCorrectDataShouldThrowExceptionTest() {
//        when(giftCertificateDao.findById(any(long.class))).thenReturn(Optional.empty());
//        when(giftCertificateDao.update(any(GiftCertificate.class))).thenReturn(Optional.of(giftCertificate1));
//        assertThrows(ResourceNotFoundException.class,
//                () -> giftCertificateService.update(giftCertificateDto6));
//    }
//
//    @Test
//    void updateGiftCertificateIncorrectDataShouldThrowExceptionTest() {
//        when(giftCertificateDao.findById(any(long.class))).thenReturn(Optional.empty());
//        when(giftCertificateDao.update(any(GiftCertificate.class))).thenReturn(Optional.of(giftCertificate1));
//        assertThrows(ResourceNotFoundException.class,
//                () -> giftCertificateService.update(giftCertificateDto7));
//    }
//
//    @Test
//    void removeGiftCertificateCorrectDataShouldNotThrowExceptionTest() {
//        long id = 1;
//        doNothing().when(giftCertificateDao).delete(any(long.class));
//        when(giftCertificateDao.findById(any(long.class))).thenReturn(Optional.of(giftCertificate1));
//        assertDoesNotThrow(() -> giftCertificateService.delete(id));
//    }
//
//}