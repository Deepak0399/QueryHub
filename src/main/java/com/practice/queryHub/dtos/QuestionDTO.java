package com.practice.queryHub.dtos;

import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class QuestionDTO {
    private UUID id;
    private String title;
    private String content;
    private UUID userId;
    private Set<UUID> tagIds;
}
