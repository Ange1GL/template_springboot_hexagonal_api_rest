package com.app.app.domain.port.in;

import com.app.app.domain.model.User;
import com.app.app.domain.shared.AuthError;
import com.app.app.domain.shared.Result;
import com.app.app.infraestructure.dto.CreateUserRequest;

public interface RegisterUserCase {
    Result<User, AuthError> register(String email, String rawPassword, String name);
}
