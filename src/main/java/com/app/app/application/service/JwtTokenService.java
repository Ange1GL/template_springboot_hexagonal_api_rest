package com.app.app.application.service;

import com.app.app.domain.model.User;
import com.app.app.domain.port.out.TokenService;
import com.app.app.infraestructure.security.excepcion.JwtAuthenticationException;
import com.app.app.infraestructure.security.excepcion.JwtErrorCode;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@RequiredArgsConstructor
public class JwtTokenService implements TokenService {
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    @Value("${security.jwt.expiration}")
    private long expirationMinutes;


    @Value("${security.jwt.refresh-expiration}")
    private long refreshExpirationMinutes;

    @Override
    public String generateToken(User user) {
        return generateJwt(user , expirationMinutes);
    }

    @Override
    public String generateRefreshToken(User user) {
        return generateJwt(user, refreshExpirationMinutes);
    }


    private String generateJwt(User user, long minutes) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(user.getEmail())
                .issuedAt(now)
                .expiresAt(now.plus(minutes, ChronoUnit.MINUTES))
                .build();

        JwtEncoderParameters params = JwtEncoderParameters.from(
                JwsHeader.with(MacAlgorithm.HS256).build(),
                claims
        );

        return jwtEncoder.encode(params).getTokenValue();
    }

    @Override
    public String getSubject(String token) {
        Jwt jwt = jwtDecoder.decode(token);
        return jwt.getSubject();
    }

    @Override
    public boolean validateToken(String token) {
        try {
            jwtDecoder.decode(token);
            return true;
        } catch (JwtValidationException ex) {
            // 🔹 Puede contener múltiples errores
            if (ex.getErrors().stream()
                    .anyMatch(error ->
                            OAuth2ErrorCodes.INVALID_TOKEN.equals(error.getErrorCode())
                    )) {
                throw new JwtAuthenticationException(JwtErrorCode.TOKEN_EXPIRED);
            }
            throw new JwtAuthenticationException(JwtErrorCode.TOKEN_INVALID);

        } catch (BadJwtException ex) {
            throw new JwtAuthenticationException(JwtErrorCode.TOKEN_MALFORMED);

        } catch (JwtException ex) {
            throw new JwtAuthenticationException(JwtErrorCode.TOKEN_INVALID);
        }
    }

}
