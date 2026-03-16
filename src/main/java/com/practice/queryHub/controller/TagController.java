package com.practice.queryHub.controller;

import com.practice.queryHub.dtos.TagDTO;
import com.practice.queryHub.model.Tag;
import com.practice.queryHub.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tags")
public class TagController {

    private final TagService tagService;

    @PostMapping
    public ResponseEntity<Tag> createTag(@RequestBody TagDTO tagDTO) {
        Tag tag = tagService.createTag(tagDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(tag);
    }
    @GetMapping
    public ResponseEntity<List<Tag>> findAllTags() {
        List<Tag> tags = tagService.getAllTags();
        return ResponseEntity.status(HttpStatus.OK).body(tags);
    }

    @GetMapping("/{tagId}")
    public ResponseEntity<Tag> getTagById(@PathVariable UUID tagId) {
        Optional<Tag> tag = tagService.getTagById(tagId);
        return tag.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{tagId}")
    public ResponseEntity<Void> deleteTag(@PathVariable UUID tagId) {
        tagService.deleteTag(tagId);
        return ResponseEntity.noContent().build();
    }
}
