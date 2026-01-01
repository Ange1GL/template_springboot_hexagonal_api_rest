package com.app.app.infraestructure.mapper;

import com.app.app.domain.model.User;
import com.app.app.infraestructure.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toDomain(UserEntity entity) {
        User user = new User();
        user.setId(entity.getId());
        user.setEmail(entity.getEmail());
        user.setName(entity.getName());
        user.setPassword(entity.getPassword());
        user.setActive(entity.isActive());
        return user;
    }

    public UserEntity toEntity(User user) {
        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setEmail(user.getEmail());
        entity.setName(user.getName());
        entity.setPassword(user.getPassword());
        entity.setActive(user.isEnabled());
        return entity;
    }


}