package com.practice.queryHub.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class CommentDTO {
    private UUID id;
    private String content;
    private UUID answerId;
    private UUID parentCommentId;
}
