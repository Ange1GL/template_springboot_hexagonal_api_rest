package com.app.app.infraestructure.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthTokenResponseDTO {
    private String token;
    private String refreshToken;
}
