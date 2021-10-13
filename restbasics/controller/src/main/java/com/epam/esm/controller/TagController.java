package com.epam.esm.controller;

import com.epam.esm.dto.CertificateTagDto;
import com.epam.esm.dto.TagCreateDto;
import com.epam.esm.service.TagService;
import com.epam.esm.dto.TagDto;


import com.epam.esm.exception.ExceptionMessageKey;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
        return tagService.findAll();
    }

    @GetMapping("/{id:\\d+}")
    public TagDto findById(@PathVariable @Range(min = 0) Long id) {
        return tagService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto create(@Valid @RequestBody TagCreateDto tagCreateDto) {
        return tagService.create(tagCreateDto);
    }

    @DeleteMapping("/{id:\\d+}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable  @Range(min = 0) Long id) {
        tagService.delete(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/attach")
    public void attachTag(@Valid @RequestBody CertificateTagDto certificateTagDto) {
        tagService.attachTag(certificateTagDto);
    }
}

