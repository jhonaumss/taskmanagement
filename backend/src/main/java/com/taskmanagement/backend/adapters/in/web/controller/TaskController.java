package com.taskmanagement.backend.adapters.in.web.controller;

import com.taskmanagement.backend.adapters.in.web.dto.TaskDTO;
import com.taskmanagement.backend.application.service.TaskService;
import com.taskmanagement.backend.application.service.UserService;
import com.taskmanagement.backend.domain.model.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<TaskDTO> create(@RequestBody TaskDTO dto,
                                          @AuthenticationPrincipal UserDetails userDetails) {
        UUID userId = userService.findByUsername(userDetails.getUsername()).get().getId();
        Task task = new Task(null, dto.title(), dto.description(), dto.completed(), userId);
        Task saved = taskService.create(task);
        return ResponseEntity.ok(toDTO(saved));
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAll(@AuthenticationPrincipal UserDetails userDetails) {
        System.out.println(userDetails.getUsername());
        UUID userId = userService.findByUsername(userDetails.getUsername()).get().getId();
        List<TaskDTO> tasks = taskService.getAllByUser(userId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> update(@PathVariable UUID id,
                                          @RequestBody TaskDTO dto,
                                          @AuthenticationPrincipal UserDetails userDetails) {
        UUID userId = userService.findByUsername(userDetails.getUsername()).get().getId();
        Task task = new Task(id, dto.title(), dto.description(), dto.completed(), userId);
        return ResponseEntity.ok(toDTO(taskService.update(task)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private TaskDTO toDTO(Task task) {
        return new TaskDTO(task.getId(), task.getTitle(), task.getDescription(), task.isCompleted(), task.getUserId());
    }
}

