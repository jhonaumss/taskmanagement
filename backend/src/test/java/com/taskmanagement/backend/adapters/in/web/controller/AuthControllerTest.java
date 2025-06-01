package com.taskmanagement.backend.adapters.in.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taskmanagement.backend.adapters.in.web.dto.AuthRequest;
import com.taskmanagement.backend.application.service.UserService;
import com.taskmanagement.backend.domain.model.User;
import com.taskmanagement.backend.security.JwtUtil;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthenticationManager authManager;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private JwtUtil jwtUtil;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldRegisterUserAndReturnToken() throws Exception {
        AuthRequest request = new AuthRequest("john", "1234");
        User user = new User(UUID.randomUUID(), "john", "1234", "USER");

        when(userService.register(any())).thenReturn(user);
        when(jwtUtil.generateToken(any(), eq("john"))).thenReturn("mocked-token");

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mocked-token"));
    }

    @Test
    void shouldLoginAndReturnToken() throws Exception {
        AuthRequest request = new AuthRequest("john", "1234");
        User user = new User(UUID.randomUUID(), "john", "1234", "USER");

        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken("john", "1234"));

        when(userService.findByUsername("john")).thenReturn(Optional.of(user));
        when(jwtUtil.generateToken(any(), eq("john"))).thenReturn("mocked-token");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mocked-token"));
    }
}
