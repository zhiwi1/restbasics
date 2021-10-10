package com.epam.esm.controller;

import com.epam.esm.service.CertificateTagService;
import com.epam.esm.service.dto.CertificateTagDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/certificate_tags")
@Validated
public class CertificateTagController {

    private final CertificateTagService certificateTagService;

    public CertificateTagController(CertificateTagService certificateTagService) {
        this.certificateTagService = certificateTagService;
    }

    @PostMapping
    public void create(@Valid @RequestBody CertificateTagDto certificateTagDto) {
        certificateTagService.addCertificateTag(certificateTagDto);
    }
}
