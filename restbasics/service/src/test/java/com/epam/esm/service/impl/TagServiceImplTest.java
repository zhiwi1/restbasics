package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.DublicateResourceException;
import com.epam.esm.service.exception.ResourceNotFoundException;
import com.epam.esm.service.mapper.ServiceTagMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;

class TagServiceImplTest {

    private static TagDao tagDao;
    private static ModelMapper modelMapper;
    private static ServiceTagMapper tagMapper;
    private static TagService tagService;
    private static Tag tag1;
    private static Tag tag2;
    private static TagDto tagDto1;
    private static TagDto tagDto2;

    @BeforeAll
    static void setUp() {
        tagDao = mock(TagDaoImpl.class);
        modelMapper = new ModelMapper();
        tagMapper = new ServiceTagMapper(modelMapper);
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
        tagService = new TagServiceImpl(tagDao, tagMapper);
        tag1 = Tag.builder()
                .id(1L)
                .name("Sport")
                .build();
        tag2 = Tag.builder()
                .id(2L)
                .name("Travel")
                .build();
        tagDto1 = TagDto.builder()
                .id(1L)
                .name("Sport")
                .build();
        tagDto2 = TagDto.builder()
                .id(2L)
                .name("Travel")
                .build();
    }

    @AfterAll
    static void tearDown() {
        tagDao = null;
        modelMapper = null;
        tagService = null;
        tag1 = null;
        tag2 = null;
        tagDto1 = null;
        tagDto2 = null;
    }

    @Test
    void addTagCorrectDataShouldReturnTagDtoTest() {
        when(tagDao.findByName(any(String.class))).thenReturn(Optional.empty());
        when(tagDao.insert(any(Tag.class))).thenReturn(Optional.ofNullable(tag1));
        TagDto actual = tagService.addTag(tagDto1);
        TagDto expected = tagDto1;
        assertEquals(expected, actual);
    }

    @Test
    void addTagIncorrectDataShouldThrowExceptionTest() {
        when(tagDao.findByName(any(String.class))).thenReturn(Optional.ofNullable(tag1));
        assertThrows(DublicateResourceException.class, () -> tagService.addTag(tagDto1));
    }

    @Test
    void findAllTagsCorrectDataShouldReturnListOfTagDtoTest() {
        int expectedSize = 2;
        when(tagDao.findAll()).thenReturn(Arrays.asList(tag1, tag2));
        List<TagDto> actual = tagService.findAllTags();
        assertEquals(expectedSize, actual.size());
    }

    @Test
    void findTagByIdCorrectDataShouldReturnTagDtoTest() {
        long id = 1;
        when(tagDao.findById(any(long.class))).thenReturn(Optional.of(tag2));
        TagDto actual = tagService.findTagById(id);
        TagDto expected = tagDto2;
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 23, 10000000, -123213})
    void findTagByIdIncorrectDataShouldThrowExceptionTest(long id) {
        when(tagDao.findById(any(long.class))).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> tagService.findTagById(id));
    }

    @ParameterizedTest
    @ValueSource(longs = {1213, 23, 10000000})
    void removeTagCorrectDataShouldNotThrowExceptionTest(long id) {
        when(tagDao.findById(any(long.class))).thenReturn(Optional.ofNullable(tag1));
        doNothing().when(tagDao).delete(any(long.class));
        assertDoesNotThrow(() -> tagService.removeTag(id));
    }

    @ParameterizedTest
    @ValueSource(longs = {1213, 23, 10000000, -123213})
    void removeTagCorrectDataShouldThrowExceptionTest(long id) {
        doNothing().when(tagDao).delete(any(long.class));
        assertThrows(ResourceNotFoundException.class, () -> tagService.removeTag(id));
    }

    @Test
    void findTagsByGiftCertificateIdCorrectDataShouldReturnListOfTagDtoTest() {
        int expected = 2;
        long giftCertificateId = 1;
        when(tagDao.findByGiftCertificateId(any(Long.class))).thenReturn(Arrays.asList(tag1, tag2));
        Set<TagDto> actual = tagService.findTagsByGiftCertificateId(giftCertificateId);
        assertEquals(expected, actual.size());
    }

    @Test
    void findTagsByGiftCertificateIdCorrectDataShouldThrowExceptionTest() {
        when(tagDao.findByGiftCertificateId(any(Long.class))).thenReturn(Arrays.asList(tag1, tag2));
        Set<TagDto> expected = new HashSet<>();
        expected.add(tagDto1);
        expected.add(tagDto2);
        long id = 1L;
        Set<TagDto> actual = tagService.findTagsByGiftCertificateId(id);
        assertEquals(expected, actual);
    }
}
