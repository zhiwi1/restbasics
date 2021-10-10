package com.epam.esm.controller;

import com.epam.esm.service.TagService;
import com.epam.esm.service.dto.TagDto;


import com.epam.esm.service.exception.ExceptionMessageKey;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;


import java.util.List;


@RestController
@Validated
@RequestMapping("/v1/tags")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @GetMapping
    public List<TagDto> findAll() {
        return tagService.findAllTags();
    }

    @GetMapping("/{id}")
    public TagDto findById(@PathVariable @Range(min = 0,message = ExceptionMessageKey.VALUE_NOT_IN_RANGE) long id) {
        return tagService.findTagById(id);
    }

    @PostMapping
    public void createTag(@Valid @RequestBody TagDto tagDto) {
        tagService.addTag(tagDto);
    }

    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable @Range(min = 0,message = ExceptionMessageKey.VALUE_NOT_IN_RANGE)  long id) {
        tagService.removeTag(id);
    }


}

