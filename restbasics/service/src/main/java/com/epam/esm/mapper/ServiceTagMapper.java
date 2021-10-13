package com.epam.esm.mapper;

import com.epam.esm.dto.TagCreateDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.dto.TagDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ServiceTagMapper {
    private final ModelMapper modelMapper;

    public Tag toEntity(TagDto tagDto) {
        return modelMapper.map(tagDto, Tag.class);
    }

    public Tag toEntity(TagCreateDto tagCreateDto) {
        return modelMapper.map(tagCreateDto, Tag.class);
    }

    public TagDto toDto(Tag tag) {
        return modelMapper.map(tag, TagDto.class);
    }
}


