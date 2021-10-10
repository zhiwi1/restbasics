//package com.epam.esm.service.impl;
//
//import com.epam.esm.entity.Tag;
//import com.epam.esm.dao.TagDao;
//import com.epam.esm.dao.impl.TagDaoImpl;
//import com.epam.esm.service.TagService;
//import com.epam.esm.service.dto.TagDto;
//import com.epam.esm.service.exception.ResourceNotFoundException;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.modelmapper.ModelMapper;
//import org.modelmapper.convention.MatchingStrategies;
//
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//import java.util.Set;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//class TagServiceImplTest {
//
//    private static TagDao tagDao;
//    private static ModelMapper modelMapper;
//    private static TagService tagService;
//    private static TagDto tagDto1;
//    private static TagDto tagDto2;
//    private static TagDto tagDto3;
//    private static Tag tag1;
//    private static Tag tag2;
//
//
//    @BeforeAll
//    static void setUp() {
//        tagDao = mock(TagDaoImpl.class);
//        modelMapper = new ModelMapper();
//        modelMapper.getConfiguration()
//                .setMatchingStrategy(MatchingStrategies.STRICT)
//                .setFieldMatchingEnabled(true)
//                .setSkipNullEnabled(true)
//                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
//        tagService = new TagServiceImpl(tagDao);
//        tag1 = Tag.builder()
//                .id(1)
//                .name("Belarus")
//                .build();
//        tag2 = Tag.builder()
//                .id(2)
//                .name("Belarus")
//                .build();
//        tagDto1 = TagDto.builder()
//                .id(2)
//                .name("Belarus")
//                .build();
//        tagDto2 = TagDto.builder()
//                .id(1L)
//                .name("Belarus")
//                .build();
//        tagDto3 = TagDto.builder()
//                .name(" ")
//                .build();
//    }
//
//    @AfterAll
//    static void makeNull() {
//        tagDao = null;
//        modelMapper = null;
//        tagService = null;
//        tag1 = null;
//        tag2 = null;
//        tagDto1 = null;
//        tagDto2 = null;
//        tagDto3 = null;
//    }
//
////    @Test
////    void addTagCorrectDataShouldReturnTagDtoTest() {
////        when(tagDao.findByName(any(String.class))).thenReturn(Optional.empty());
////        when(tagDao.insert(any(Tag.class))).thenReturn(Optional.ofNullable(tag1));
////        TagDto actual = tagService.addTag(tagDto1);
////        assertEquals(tagDto2, actual);
////    }
//
//    @Test
//    void findAllTagsCorrectDataShouldReturnListOfTagDtoTest() {
//        int expected = 2;
//        when(tagDao.findAll()).thenReturn(Arrays.asList(tag1, tag2));
//        List<TagDto> actual = tagService.findAllTags();
//        assertEquals(expected, actual.size());
//    }
//
//    @Test
//    void findTagByIdCorrectDataShouldReturnTagDtoTest() {
//        long id = 1;
//        when(tagDao.findById(any(Long.class))).thenReturn(Optional.of(tag1));
//        TagDto actual = tagService.findTagById(id);
//        assertEquals(tagDto2, actual);
//    }
////
////    @Test
////    void findTagByIdCorrectDataShouldThrowExceptionTest() {
////        long id = 5;
////        when(tagDao.findById(any(Long.class))).thenReturn(Optional.empty());
////        assertThrows(ResourceNotFoundException.class, () -> tagService.findTagById(id));
////    }
//
////    @Test
////    void removeTagCorrectDataShouldNotThrowExceptionTest() {
////        long id = 1;
////        doNothing().when(tagDao).removeTaggedGiftCertificate(any(Long.class));
////        doNothing().when(tagDao).delete(any(Long.class));
////        assertDoesNotThrow(() -> tagService.removeTag(id));
////    }
//
//    @Test
//    void findTagsByGiftCertificateIdCorrectDataShouldReturnListOfTagDtoTest() {
//        int expected = 2;
//        long giftCertificateId = 1;
//        when(tagDao.findByGiftCertificateId(any(Long.class))).thenReturn(Arrays.asList(tag1, tag2));
//        Set<TagDto> actual = tagService.findTagsByGiftCertificateId(giftCertificateId);
//        assertEquals(expected, actual.size());
//    }
//
//}