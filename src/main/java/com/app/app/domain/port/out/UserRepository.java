package com.app.app.domain.port.out;

import com.app.app.domain.model.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    User save(User user);

    boolean existsByEmail(String email);
}
