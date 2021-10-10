package com.epam.esm.controller;


import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.dto.GiftCertificateQueryParamDto;
import com.epam.esm.service.exception.ExceptionMessageKey;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/v1/certificates")
@Validated
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;

    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @GetMapping
    public List<GiftCertificateDto> getGiftCertificates(@Valid @RequestBody
            GiftCertificateQueryParamDto giftCertificateQueryParametersDto) {
        return giftCertificateService.findGiftCertificates(giftCertificateQueryParametersDto);
    }

    @GetMapping("/{id}")
    public GiftCertificateDto findById(@Range(min = 0,message = ExceptionMessageKey.VALUE_NOT_IN_RANGE) @PathVariable long id) {
        return giftCertificateService.findById(id);
    }
    @PostMapping("/")
    public void create(@Valid @RequestBody GiftCertificateDto giftCertificate) {
        giftCertificateService.add(giftCertificate);
    }

    @PutMapping("/")
    public void update(@Valid @RequestBody GiftCertificateDto giftCertificate) {
        giftCertificateService.update(giftCertificate);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable @Range(min = 0,message = ExceptionMessageKey.VALUE_NOT_IN_RANGE) long id) {
        giftCertificateService.remove(id);
    }
}

