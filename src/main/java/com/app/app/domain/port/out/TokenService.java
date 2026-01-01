package com.app.app.domain.port.out;

import com.app.app.domain.model.User;

public interface TokenService {
    String generateToken(User user);

    String getSubject(String token);

    boolean validateToken(String token);


    String generateRefreshToken(User user);
}
