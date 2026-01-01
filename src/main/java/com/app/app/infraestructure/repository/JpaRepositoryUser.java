package com.app.app.infraestructure.repository;

import com.app.app.domain.model.User;
import com.app.app.infraestructure.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface JpaRepositoryUser extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);
}
