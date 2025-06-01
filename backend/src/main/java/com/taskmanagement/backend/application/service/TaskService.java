package com.taskmanagement.backend.application.service;

import com.taskmanagement.backend.domain.model.Task;
import com.taskmanagement.backend.domain.port.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public Task create(Task task) {
        task.setId(UUID.randomUUID());
        return taskRepository.save(task);
    }

    public List<Task> getAllByUser(UUID userId) {
        return taskRepository.findAllByUserId(userId);
    }

    public Optional<Task> getById(UUID taskId) {
        return taskRepository.findById(taskId);
    }

    public Task update(Task task) {
        return taskRepository.save(task);
    }

    public void delete(UUID taskId) {
        taskRepository.deleteById(taskId);
    }
}

