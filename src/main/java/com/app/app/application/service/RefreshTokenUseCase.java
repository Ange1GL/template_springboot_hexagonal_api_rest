package com.app.app.application.service;

import com.app.app.domain.model.User;
import com.app.app.domain.port.out.TokenService;
import com.app.app.domain.port.out.UserRepository;
import com.app.app.infraestructure.security.excepcion.JwtAuthenticationException;
import com.app.app.infraestructure.security.excepcion.JwtErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RefreshTokenUseCase {
    private  final UserRepository userRepository;
    private  final TokenService tokenService;

    public String refreshToken(String refreshToken) {

        if(!tokenService.validateToken(refreshToken)) {
            throw  new JwtAuthenticationException(JwtErrorCode.TOKEN_INVALID);
        }



        String email = tokenService.getSubject(refreshToken);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new JwtAuthenticationException(JwtErrorCode.TOKEN_INVALID));

        return  tokenService.generateToken(user.getEmail());

    }
}
