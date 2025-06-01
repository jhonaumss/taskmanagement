package com.taskmanagement.backend.domain.model;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private UUID id;
    private String username;
    private String password;
    private String role; // e.g., "USER", "ADMIN"
}

