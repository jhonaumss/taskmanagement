package com.taskmanagement.backend.adapters.out.persistence;

import com.taskmanagement.backend.adapters.out.persistence.mapper.UserMapper;
import com.taskmanagement.backend.adapters.out.persistence.repository.JpaUserRepository;
import com.taskmanagement.backend.domain.model.User;
import com.taskmanagement.backend.domain.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserRepositoryImplementation implements UserRepository {

    private final JpaUserRepository jpaRepo;

    @Override
    public Optional<User> findByUsername(String username) {
        return jpaRepo.findByUsername(username).map(UserMapper::toDomain);
    }

    @Override
    public User save(User user) {
        return UserMapper.toDomain(jpaRepo.save(UserMapper.toEntity(user)));
    }

    @Override
    public Optional<User> findById(UUID id) {
        return jpaRepo.findById(id).map(UserMapper::toDomain);
    }
}
