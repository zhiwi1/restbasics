package com.epam.esm.service.mapper;

import com.epam.esm.entity.Tag;
import com.epam.esm.service.dto.TagDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServiceTagMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public ServiceTagMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Tag toEntity(TagDto tagDto) {
        return modelMapper.map(tagDto, Tag.class);
    }

    public TagDto toDto(Tag tag) {
        return modelMapper.map(tag, TagDto.class);
    }
}


