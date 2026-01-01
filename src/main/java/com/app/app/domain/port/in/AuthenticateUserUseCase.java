package com.app.app.domain.port.in;

import com.app.app.domain.model.User;
import com.app.app.domain.shared.AuthError;
import com.app.app.domain.shared.Result;

public interface AuthenticateUserUseCase {
    Result<User, AuthError> authenticate(String email, String rawPassword);
}
