package com.practice.queryhub.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;


@Data
@MappedSuperclass
public class BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
}
