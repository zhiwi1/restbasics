package com.epam.esm.controller;


import com.epam.esm.dto.GiftCertificateInputDto;
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
    public List<GiftCertificateDto> findGiftCertificates(@Valid @RequestBody GiftCertificateQueryParamDto giftCertificateQueryParametersDto) {
        return giftCertificateService.findGiftCertificates(giftCertificateQueryParametersDto);
    }

    @GetMapping("/{id:\\d+}")
    public GiftCertificateDto findById(@Range(min = 0, message = ExceptionMessageKey.VALUE_NOT_IN_RANGE) @PathVariable long id) {
        return giftCertificateService.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public GiftCertificateDto create(@Valid @RequestBody GiftCertificateInputDto giftCertificate) {
        return giftCertificateService.create(giftCertificate);
    }

    @PatchMapping("/{id:\\d+}")
    public GiftCertificateDto update(@PathVariable  @Range(min = 0, message = ExceptionMessageKey.VALUE_NOT_IN_RANGE) Long id, @Valid @RequestBody GiftCertificateInputDto giftCertificate) {
        return giftCertificateService.update(id,giftCertificate);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id:\\d+}")
    public void delete(@PathVariable @Range(min = 0, message = ExceptionMessageKey.VALUE_NOT_IN_RANGE) Long id) {
        giftCertificateService.delete(id);
    }
}

