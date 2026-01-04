package com.app.app.infraestructure.controller;

import com.app.app.application.service.RefreshTokenUseCase;
import com.app.app.domain.model.User;
import com.app.app.domain.port.in.AuthenticateUserUseCase;
import com.app.app.domain.port.in.RegisterUserCase;
import com.app.app.domain.port.out.TokenService;
import com.app.app.domain.shared.AuthError;
import com.app.app.domain.shared.Result;
import com.app.app.infraestructure.dto.*;
import com.app.app.infraestructure.mapper.AuthResultHttpMapper;
import com.app.app.infraestructure.util.CookieUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticateUserUseCase authenticateUserUseCase;
    private  final TokenService tokenService;
    private final CookieUtil cookieUtil;
    private final RegisterUserCase registerUserCase;
    private final AuthResultHttpMapper authResultHttpMapper;
    private  final RefreshTokenUseCase refreshTokenUseCase;

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO<AuthTokenResponseDTO>> login(@RequestBody LoginRequest request, HttpServletResponse response) {

        Result<User, AuthError> result =
                authenticateUserUseCase.authenticate(
                        request.email(),
                        request.password()
                );


        if (result.isFailure()) {
            return authResultHttpMapper.toResponse(result, null, null);
        }

        User user = result.getValue();

        String token = tokenService.generateToken(user.getEmail());
        String refreshToken = tokenService.generateRefreshToken(user.getEmail());

        response.addCookie(cookieUtil.createAuthCookie(token));

        return authResultHttpMapper.toResponse(
                result,
                token,
                refreshToken
        );
    }


    @PostMapping("/register")
    public ResponseEntity<ResponseDTO<AuthTokenResponseDTO>> register(@RequestBody CreateUserRequest request) {

        Result<User, AuthError> result = registerUserCase.register(
                request.getEmail(),
                request.getPassword(),
                request.getName()
        );

        if (result.isFailure()) {
            return authResultHttpMapper.toResponse(result, null, null);
        }

         User  registeredUser = result.getValue();

        final String token = tokenService.generateToken(registeredUser.getEmail());
        final String refreshToken = tokenService.generateRefreshToken(registeredUser.getEmail());

        AuthTokenResponseDTO authTokenResponseDTO =AuthTokenResponseDTO.builder().token(token).refreshToken(refreshToken).build();

        ResponseDTO<AuthTokenResponseDTO> responseDTO = ResponseDTO.<AuthTokenResponseDTO>builder()
                .responseMessage("Login successful")
                .data(authTokenResponseDTO)
                .build();

        return ResponseEntity.ok(responseDTO);
    }


    @PostMapping("/refresh")
    public ResponseEntity<Void> refreshToken(
            @RequestBody RefreshRequest request,
            HttpServletResponse response) {

        String refreshToken = request.getRefreshToken();

        if (!tokenService.validateToken(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String newJwt = refreshTokenUseCase.refreshToken(refreshToken);
        response.addCookie(cookieUtil.createAuthCookie(newJwt));

        return ResponseEntity.ok().build();
    }
}