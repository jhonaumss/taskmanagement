package com.taskmanagement.backend.adapters.out.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskEntity {

    @Id
    private UUID id;

    private String title;
    private String description;
    private boolean completed;

    @Column(nullable = false)
    private UUID userId;
}

