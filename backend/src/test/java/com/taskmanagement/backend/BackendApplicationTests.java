package com.taskmanagement.backend;

import com.taskmanagement.backend.adapters.out.persistence.repository.JpaTaskRepository;
import com.taskmanagement.backend.adapters.out.persistence.repository.JpaUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
@ActiveProfiles("test")
class BackendApplicationTests {

	@MockitoBean
	private JpaUserRepository jpaUserRepository;

	@MockitoBean
	private JpaTaskRepository jpaTaskRepository;

	@Test
	void contextLoads() {
	}

}
