package com.practice.queryHub.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class AnswerDTO {
    private UUID id;
    private String content;
    private UUID userId;
    private UUID questionId;
}
