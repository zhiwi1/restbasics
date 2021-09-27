package com.epam.esm.controller;

import com.epam.esm.controller.entity.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certificate")
public class GiftCertificateController {

    private GiftCertificateService giftCertificateService;

    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @GetMapping("/findall")
    public List<GiftCertificate> findAll() {
        return giftCertificateService.findAll();

    }

    @GetMapping("/{id}")
    public GiftCertificate findById(@PathVariable Long id) {
        return giftCertificateService.findById(id);
    }

    @PostMapping
    public void create(@RequestBody GiftCertificate giftCertificate) {
        giftCertificateService.insert(giftCertificate);
    }

    @PutMapping("/update")
    public void update(@RequestBody GiftCertificate giftCertificate, @RequestBody long id) {
        giftCertificateService.update(id, giftCertificate);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestBody GiftCertificate giftCertificate) {
        giftCertificateService.delete(giftCertificate);
    }
}

