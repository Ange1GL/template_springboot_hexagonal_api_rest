package com.app.app.application.service;


import com.app.app.domain.model.User;
import com.app.app.domain.port.in.AuthenticateUserUseCase;
import com.app.app.domain.port.out.LoadUserByEmailPort;
import com.app.app.domain.port.out.PasswordHasher;
import com.app.app.domain.shared.AuthError;
import com.app.app.domain.shared.Result;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthenticateUserService implements AuthenticateUserUseCase {

    private final LoadUserByEmailPort loadUserByEmailPort;
    private final PasswordHasher passwordEncoder;


    @Override
    public Result<User, AuthError> authenticate(String email, String rawPassword) {

        User user = loadUserByEmailPort.loadByEmail(email);

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            return Result.failure(AuthError.INVALID_CREDENTIALS);
        }

        if (!user.isEnabled()) {
            return Result.failure(AuthError.USER_DISABLED);
        }

        return Result.success(user);
    }
}