package com.taskmanagement.backend.adapters.out.persistence.mapper;

import com.taskmanagement.backend.adapters.out.persistence.entity.TaskEntity;
import com.taskmanagement.backend.domain.model.Task;
public class TaskMapper {
    public static TaskEntity toEntity(Task task) {
        return TaskEntity.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .completed(task.isCompleted())
                .userId(task.getUserId())
                .build();
    }

    public static Task toDomain(TaskEntity entity) {
        return Task.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .completed(entity.isCompleted())
                .userId(entity.getUserId())
                .build();
    }
}

