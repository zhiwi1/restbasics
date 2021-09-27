package com.epam.esm.controller;

import com.epam.esm.controller.entity.Tag;
import com.epam.esm.service.TagService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {
    private TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/find")
    public List<Tag> findAll() {
        return tagService.findAll();
    }

    @GetMapping("/{id}")
    public Tag findById(@PathVariable long id) {
        return tagService.findById(id);
    }

    @PostMapping("/create")
    public void createTag(@RequestBody Tag tag) {
        tagService.insert(tag);
    }

    @DeleteMapping("/delete")
    public void deleteTag(@RequestBody long id) {
        tagService.deleteById(id);
    }

}

