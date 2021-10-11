package com.epam.esm.controller;


import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateQueryParamDto;
import com.epam.esm.exception.ExceptionMessageKey;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/v1/certificates")
@Validated
@RequiredArgsConstructor
public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;

    @GetMapping
    public List<GiftCertificateDto> getGiftCertificates(@Valid @RequestBody
                                                                GiftCertificateQueryParamDto giftCertificateQueryParametersDto) {
        return giftCertificateService.findGiftCertificates(giftCertificateQueryParametersDto);
    }

    @GetMapping("/{id}")
    public GiftCertificateDto findById(@Range(min = 0, message = ExceptionMessageKey.VALUE_NOT_IN_RANGE) @PathVariable long id) {
        return giftCertificateService.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void create(@Valid @RequestBody GiftCertificateDto giftCertificate) {
        giftCertificateService.create(giftCertificate);
    }

    @PutMapping
    public void update(@Valid @RequestBody GiftCertificateDto giftCertificate) {
        giftCertificateService.update(giftCertificate);
    }
    @PatchMapping
    public GiftCertificateDto updateCertificate(@Validated(PatchDto.class) @RequestBody GiftCertificateDto certificate) {
        return certificateService.updateCertificate(certificate);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable @Range(min = 0, message = ExceptionMessageKey.VALUE_NOT_IN_RANGE) long id) {
        giftCertificateService.delete(id);
    }
}

