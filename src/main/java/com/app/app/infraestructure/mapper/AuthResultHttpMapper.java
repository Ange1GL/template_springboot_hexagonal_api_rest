package com.app.app.infraestructure.mapper;

import com.app.app.domain.model.User;
import com.app.app.domain.shared.AuthError;
import com.app.app.domain.shared.Result;
import com.app.app.infraestructure.dto.AuthTokenResponseDTO;
import com.app.app.infraestructure.dto.ErrorDetailDTO;
import com.app.app.infraestructure.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthResultHttpMapper {

    public ResponseEntity<ResponseDTO<AuthTokenResponseDTO>> toResponse(
            Result<User, AuthError> result,
            String token,
            String refreshToken
    ) {

        if (result.isSuccess()) {
            return success(token, refreshToken);
        }

        return failure(result.getError());
    }

    /* ================= SUCCESS ================= */

    private ResponseEntity<ResponseDTO<AuthTokenResponseDTO>> success(
            String token,
            String refreshToken
    ) {
        AuthTokenResponseDTO data = AuthTokenResponseDTO.builder()
                .token(token)
                .refreshToken(refreshToken)
                .build();

        ResponseDTO<AuthTokenResponseDTO> response = ResponseDTO.<AuthTokenResponseDTO>builder()
                .responseMessage("Login successful")
                .data(data)
                .errorList(null)
                .build();

        return ResponseEntity.ok(response);
    }

    /* ================= FAILURE ================= */

    private ResponseEntity<ResponseDTO<AuthTokenResponseDTO>> failure(AuthError error) {

        ErrorDetailDTO errorDetail = new ErrorDetailDTO(
                mapHttpStatus(error),
                mapMessage(error),
                "AUTH"
        );

        ResponseDTO<AuthTokenResponseDTO> response = ResponseDTO.<AuthTokenResponseDTO>builder()
                .responseMessage("Authentication failed")
                .data(null)
                .errorList(List.of(errorDetail))
                .build();

        return ResponseEntity
                .status(mapHttpStatus(error))
                .body(response);
    }

    /* ================= MAPPINGS ================= */

    private int mapHttpStatus(AuthError error) {
        return switch (error) {
            case INVALID_CREDENTIALS -> HttpStatus.UNAUTHORIZED.value();
            case USER_DISABLED -> HttpStatus.FORBIDDEN.value();
            case EMAIL_ALREADY_IN_USE -> HttpStatus.CONFLICT.value();
        };
    }

    private String mapMessage(AuthError error) {
        return switch (error) {
            case INVALID_CREDENTIALS -> "Invalid credentials";
            case USER_DISABLED -> "User account is disabled";
            case EMAIL_ALREADY_IN_USE -> "Email is already in use";
        };
    }
}
