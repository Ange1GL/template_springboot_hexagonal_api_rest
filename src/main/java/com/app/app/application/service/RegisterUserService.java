package com.app.app.application.service;

import com.app.app.domain.model.User;
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
            return Result.failure(AuthError.EMAIL_ALREADY_IN_USE);
        }

        String hashedPassword = passwordHasher.hash(rawPassword);

        User user = User.builder()
                .email(email)
                .password(hashedPassword)
                .name(name)
                .active(true)
                .build();

        user =  userRepository.save(user);
        return Result.success(user);
    }
}
