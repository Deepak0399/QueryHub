package com.practice.queryHub.services.Impl;

import com.practice.queryHub.dtos.TagDTO;
import com.practice.queryHub.exception.TagNotFoundException;
import com.practice.queryHub.model.Tag;
import com.practice.queryHub.repositories.TagRepository;
import com.practice.queryHub.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    @Override
    public Tag createTag(TagDTO tagDTO) {
        Tag tag = new Tag();
        tag.setName(tagDTO.getName());
        tag.setId(UUID.randomUUID());
        return tagRepository.save(tag);
    }

    @Override
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    @Override
    public Optional<Tag> getTagById(UUID tagId) {
        Optional<Tag> tag = tagRepository.findById(tagId);
        if (tag.isPresent()) {
            return tag;
        }
        else {
            throw new TagNotFoundException("Tag Not Found");
        }
    }

    @Override
    public void deleteTag(UUID tagId) {
        if (tagRepository.findById(tagId).isPresent()) {
            tagRepository.deleteById(tagId);
        }
        else {
            throw new TagNotFoundException("Tag Not Found");
        }

    }
}
