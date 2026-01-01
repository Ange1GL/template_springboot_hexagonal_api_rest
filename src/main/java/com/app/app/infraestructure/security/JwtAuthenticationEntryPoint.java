package com.app.app.infraestructure.security;

import com.app.app.infraestructure.dto.ErrorDetailDTO;
import com.app.app.infraestructure.dto.ResponseDTO;
import com.app.app.infraestructure.security.excepcion.JwtAuthenticationException;
import com.app.app.infraestructure.security.excepcion.JwtErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {

        JwtErrorCode errorCode = JwtErrorCode.TOKEN_INVALID;

        if (authException.getCause() instanceof JwtAuthenticationException jwtEx) {
            errorCode = jwtEx.getErrorCode();
        }

        ErrorDetailDTO errorDetail = new ErrorDetailDTO(
                errorCode.getHttpStatus(),
                errorCode.getMessage(),
                "JWT"
        );

        ResponseDTO<Void> responseDTO = ResponseDTO.<Void>builder()
                .responseMessage("Authentication failed")
                .data(null)
                .errorList(List.of(errorDetail))
                .build();

        response.setStatus(errorCode.getHttpStatus());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        objectMapper.writeValue(response.getOutputStream(), responseDTO);
    }
}
