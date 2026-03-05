package com.practice.queryHub.services;

import com.practice.queryHub.dtos.TagDTO;
import com.practice.queryHub.model.Tag;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface TagService {

    Tag createTag(TagDTO tagDTO);
    List<Tag> getAllTags();
    Optional<Tag> getTagById(UUID tagId);
    void deleteTag(UUID tagId);

}
