package com.app.app.application.service;

import com.app.app.domain.model.User;
import com.app.app.domain.port.in.LoadUserByEmailUseCase;
import com.app.app.domain.port.in.RegisterUserCase;
import com.app.app.domain.port.out.PasswordHasher;
import com.app.app.domain.port.out.UserRepository;
import com.app.app.domain.shared.AuthError;
import com.app.app.domain.shared.Result;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RegisterUserService  implements RegisterUserCase {


    private final PasswordHasher passwordHasher;
    private final UserRepository userRepository;

    @Override
    public Result<User, AuthError> register(String email, String rawPassword, String name) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalStateException("Email already in use");
        }

        String hashedPassword = passwordHasher.hash(rawPassword);

        User user = new User(
                null,
                email,
                name,
                hashedPassword,
                true
        );

        user =  userRepository.save(user);
        return Result.success(user);
    }
}
