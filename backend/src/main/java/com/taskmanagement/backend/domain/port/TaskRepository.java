package com.taskmanagement.backend.domain.port;

import com.taskmanagement.backend.domain.model.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepository {
    Task save(Task task);
    List<Task> findAllByUserId(UUID userId);
    Optional<Task> findById(UUID id);
    void deleteById(UUID id);
}

