package com.app.app.infraestructure.adapter;


import com.app.app.domain.model.User;
import com.app.app.domain.port.out.UserRepository;
import com.app.app.infraestructure.entities.UserEntity;
import com.app.app.infraestructure.mapper.UserMapper;
import com.app.app.infraestructure.repository.JpaRepositoryUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JpaUserRepositoryAdapter implements UserRepository {

    private final JpaRepositoryUser jpaRepositoryUser;
    private final UserMapper userMapper;


    @Override
    public Optional<User> findById(Long id) {
        return jpaRepositoryUser.findById(id)
                .map(userMapper::toDomain);
    }



    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepositoryUser.findByEmail(email)
                .map(userMapper::toDomain);
    }


    @Override
    public User save(User user) {
        UserEntity entity = userMapper.toEntity(user);
        UserEntity savedEntity = jpaRepositoryUser.save(entity);
        return userMapper.toDomain(savedEntity);
    }


    @Override
    public boolean existsByEmail(String email) {
        return jpaRepositoryUser.existsByEmail(email);
    }

//    Optional<User> findByEmail(String email);
//
//    User save(User user);
//
//    boolean existsByEmail(String email);
}
