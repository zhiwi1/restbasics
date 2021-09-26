package com.epam.esm.controller;

public class CertificateController {

        private final GiftCertificateService giftCertificateService;

        @Autowired
        public CertificateController(GiftCertificateService giftCertificateService) {
            this.giftCertificateService = giftCertificateService;
        }

    }
