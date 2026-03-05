package com.practice.queryHub.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class TagDTO {
    private UUID id;
    private String name;
}
