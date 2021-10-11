package com.epam.esm.service.mapper;

import com.epam.esm.entity.Tag;
import com.epam.esm.service.dto.TagDto;
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

    public TagDto toDto(Tag tag) {
        return modelMapper.map(tag, TagDto.class);
    }
}


