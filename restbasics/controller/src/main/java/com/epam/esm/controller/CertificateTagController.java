package com.epam.esm.controller;

import com.epam.esm.service.CertificateTagService;
import com.epam.esm.service.dto.CertificateTagDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/certificate_tags")
@Validated
@RequiredArgsConstructor
public class CertificateTagController {
    private final CertificateTagService certificateTagService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void create(@Valid @RequestBody CertificateTagDto certificateTagDto) {
        certificateTagService.addCertificateTag(certificateTagDto);
    }
}
