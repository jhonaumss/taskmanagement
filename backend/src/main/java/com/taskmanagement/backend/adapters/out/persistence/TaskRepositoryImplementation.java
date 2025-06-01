package com.taskmanagement.backend.adapters.out.persistence;

import com.taskmanagement.backend.adapters.out.persistence.mapper.TaskMapper;
import com.taskmanagement.backend.adapters.out.persistence.repository.JpaTaskRepository;
import com.taskmanagement.backend.domain.model.Task;
import com.taskmanagement.backend.domain.port.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TaskRepositoryImplementation implements TaskRepository {

    private final JpaTaskRepository jpaRepo;

    @Override
    public Task save(Task task) {
        return TaskMapper.toDomain(jpaRepo.save(TaskMapper.toEntity(task)));
    }

    @Override
    public List<Task> findAllByUserId(UUID userId) {
        return jpaRepo.findAllByUserId(userId).stream()
                .map(TaskMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Task> findById(UUID id) {
        return jpaRepo.findById(id).map(TaskMapper::toDomain);
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepo.deleteById(id);
    }
}

