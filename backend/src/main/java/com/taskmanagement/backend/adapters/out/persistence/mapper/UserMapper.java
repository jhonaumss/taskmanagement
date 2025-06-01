package com.taskmanagement.backend.adapters.out.persistence.mapper;

import com.taskmanagement.backend.adapters.out.persistence.entity.UserEntity;
import com.taskmanagement.backend.domain.model.User;

public class UserMapper {
    public static UserEntity toEntity(User user) {
        return UserEntity.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }

    public static User toDomain(UserEntity entity) {
        return User.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .role(entity.getRole())
                .build();
    }
}

