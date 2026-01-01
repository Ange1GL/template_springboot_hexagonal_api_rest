package com.app.app.domain.port.in;

import com.app.app.domain.model.User;

public interface LoadUserByEmailUseCase {
    User loadByEmail(String email);
}
