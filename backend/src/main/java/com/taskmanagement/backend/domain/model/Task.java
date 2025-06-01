package com.taskmanagement.backend.domain.model;

import lombok.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
    private UUID id;
    private String title;
    private String description;
    private boolean completed;
    private UUID userId; // Reference to owning user
}

