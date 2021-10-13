package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.dto.TagCreateDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.DublicateResourceException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.ServiceTagMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TagServiceImplTest {

    private static TagDao tagDao;
    private static GiftCertificateDao certificateDao;
    private static ModelMapper modelMapper;
    private static ServiceTagMapper tagMapper;
    private static TagService tagService;

    @BeforeAll
    static void setUp() {
        tagDao = mock(TagDaoImpl.class);
        certificateDao = mock(GiftCertificateDaoImpl.class);
        modelMapper = new ModelMapper();
        tagMapper = new ServiceTagMapper(modelMapper);
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
//        tagService = new TagServiceImpl(tagDao, tagMapper);
        tagService = spy(new TagServiceImpl(tagDao, certificateDao, tagMapper));

    }

    @AfterAll
    static void tearDown() {
        tagDao = null;
        modelMapper = null;
        tagService = null;
    }

    public static Object[][] createTags() {
        return new Object[][]{
                {new Tag(1, "ab"), new TagCreateDto("ab"), new TagDto(1, "ab")},
                {new Tag(1, "bc"), new TagCreateDto("bc"), new TagDto(1, "bc")},
                {new Tag(1, "cd"), new TagCreateDto("cd"), new TagDto(1, "cd")}
        };
    }

    public static Object[][] createTagsForDublicateException() {
        return new Object[][]{
                {new Tag(1, "ab"), new TagDto(1, "ab")},
                {new Tag(5, "bc"), new TagDto(5, "bc")}
        };
    }

    //todo spy
    @ParameterizedTest
    @MethodSource("createTags")
    void addTagCorrectDataShouldReturnTagDtoTest(Tag tag1, TagCreateDto tagCreateDto, TagDto tagDto1) {
        when(tagDao.findByName(any(String.class))).thenReturn(Optional.empty());
        when(tagDao.create(any(Tag.class))).thenReturn(tag1);
        TagDto actual = tagService.create(tagCreateDto);
        TagDto expected = tagDto1;
        assertEquals(expected, actual);
    }

    @Test
    void addTagIncorrectDataShouldThrowExceptionTest() {
        when(tagDao.findByName(any(String.class))).thenReturn(Optional.ofNullable(tag1));
        assertThrows(DublicateResourceException.class, () -> tagService.create(tagDto1));
    }

//    @Test
//    void findAllTagsCorrectDataShouldReturnListOfTagDtoTest() {
//        int expectedSize = 2;
//        when(tagDao.findAll()).thenReturn(Arrays.asList(tag1, tag2));
//        List<TagDto> actual = tagService.findAll();
//        assertEquals(expectedSize, actual.size());
//    }
//
//    @Test
//    void findTagByIdCorrectDataShouldReturnTagDtoTest() {
//        long id = 1;
//        when(tagDao.findById(any(long.class))).thenReturn(Optional.of(tag2));
//        TagDto actual = tagService.findById(id);
//        TagDto expected = tagDto2;
//        assertEquals(expected, actual);
//    }
//
//    @ParameterizedTest
//    @ValueSource(longs = {1, 23, 10000000, -123213})
//    void findTagByIdIncorrectDataShouldThrowExceptionTest(long id) {
//        when(tagDao.findById(any(long.class))).thenReturn(Optional.empty());
//        assertThrows(ResourceNotFoundException.class, () -> tagService.findById(id));
//    }
//
//    @ParameterizedTest
//    @ValueSource(longs = {1213, 23, 10000000})
//    void removeTagCorrectDataShouldNotThrowExceptionTest(long id) {
//        when(tagDao.findById(any(long.class))).thenReturn(Optional.ofNullable(tag1));
//        doNothing().when(tagDao).delete(any(long.class));
//        assertDoesNotThrow(() -> tagService.delete(id));
//    }
//
//    @ParameterizedTest
//    @ValueSource(longs = {1213, 23, 10000000, -123213})
//    void removeTagCorrectDataShouldThrowExceptionTest(long id) {
//        doNothing().when(tagDao).delete(any(long.class));
//        assertThrows(ResourceNotFoundException.class, () -> tagService.delete(id));
//    }
//
//    @Test
//    void findTagsByGiftCertificateIdCorrectDataShouldReturnListOfTagDtoTest() {
//        int expected = 2;
//        long giftCertificateId = 1;
//        when(tagDao.findByCertificateId(any(Long.class))).thenReturn(Arrays.asList(tag1, tag2));
//        Set<TagDto> actual = tagService.findTagsByGiftCertificateId(giftCertificateId);
//        assertEquals(expected, actual.size());
//    }
//
//    @Test
//    void findTagsByGiftCertificateIdCorrectDataShouldThrowExceptionTest() {
//        when(tagDao.findByCertificateId(any(Long.class))).thenReturn(Arrays.asList(tag1, tag2));
//        Set<TagDto> expected = new HashSet<>();
//        expected.add(tagDto1);
//        expected.add(tagDto2);
//        long id = 1L;
//        Set<TagDto> actual = tagService.findTagsByGiftCertificateId(id);
//        assertEquals(expected, actual);
//    }
}
