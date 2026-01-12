package com.app.app.domain.port.out;

import com.app.app.domain.model.User;

public interface LoadUserByEmailPort {
    User loadByEmail(String email);
}
