package com.taskmanagement.backend.adapters.in.web.dto;

import java.util.UUID;

public record TaskDTO(
        UUID id,
        String title,
        String description,
        boolean completed,
        UUID userId
) {}

