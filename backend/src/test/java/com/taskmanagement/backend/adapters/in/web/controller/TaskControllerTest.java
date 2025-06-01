package com.taskmanagement.backend.adapters.in.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taskmanagement.backend.adapters.in.web.dto.TaskDTO;
import com.taskmanagement.backend.application.service.TaskService;
import com.taskmanagement.backend.application.service.UserService;
import com.taskmanagement.backend.domain.model.Task;
import com.taskmanagement.backend.domain.model.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;

    @MockitoBean
    private UserService userService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @WithMockUser(username = "john")
    void shouldCreateTask() throws Exception {
        UUID userId = UUID.randomUUID();
        TaskDTO dto = new TaskDTO(null, "Title", "Desc", false, userId);
        Task saved = new Task(UUID.randomUUID(), "Title", "Desc", false, userId);

        when(userService.findByUsername("john")).thenReturn(Optional.of(new User(userId, "john", "pass", "USER")));
        when(taskService.create(any())).thenReturn(saved);

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Title"));
    }

    @Test
    @WithMockUser(username = "john")
    void shouldGetTasks() throws Exception {
        UUID userId = UUID.randomUUID();
        List<Task> tasks = List.of(new Task(UUID.randomUUID(), "T1", "Desc", false, userId));

        when(userService.findByUsername("john")).thenReturn(Optional.of(new User(userId, "john", "pass", "USER")));
        when(taskService.getAllByUser(userId)).thenReturn(tasks);

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("T1"));
    }
}

