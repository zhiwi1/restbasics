package com.epam.esm.controller;

public class TagController {
        private final TagService tagService;


        @Autowired
        public TagController(TagService tagService) {
            this.tagService = tagService;
        }
}
