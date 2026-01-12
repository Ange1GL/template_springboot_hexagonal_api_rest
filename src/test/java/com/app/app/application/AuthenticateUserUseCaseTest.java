package com.app.app.application;

import com.app.app.application.service.AuthenticateUserService;
import com.app.app.domain.model.User;

import com.app.app.domain.port.out.LoadUserByEmailPort;
import com.app.app.domain.port.out.PasswordHasher;
import com.app.app.domain.shared.AuthError;
import com.app.app.domain.shared.Result;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class AuthenticateUserUseCaseTest {


    @Mock
    private PasswordHasher passwordHasher;

    @Mock
    private LoadUserByEmailPort loadUserByEmailPort;

    @InjectMocks
    AuthenticateUserService authenticateUserService;



    @Test
    @DisplayName("Shoulld authenticaste user")
    void authenticasteUser_success() {

        //given
        final String email = "lariosacostaaa@gmail.com";
        final String password = "12345angel";
        User user = User.builder()
                .id(1L)
                .email(email)
                .password("hashedPassword")
                .active(true)
                .build();
        when(loadUserByEmailPort.loadByEmail(email)).thenReturn(user);
        when(passwordHasher.matches(password, user.getPassword())).thenReturn(true);
        Result<User, AuthError> result = authenticateUserService.authenticate(email, password);
        assertTrue(result.isSuccess());
        verify(loadUserByEmailPort).loadByEmail(email);
        verify(passwordHasher).matches(password, user.getPassword());


    }


    @Test
    @DisplayName("Shoulld show error bad credencials")
    void authenticasteUser_failed() {

        //given
        final String email = "lariosacostaaa@gmail.com";
        final String password = "12345angel";
        User user = User.builder()
                .id(1L)
                .email(email)
                .password("hashedPassword")
                .active(true)
                .build();
        when(loadUserByEmailPort.loadByEmail(email)).thenReturn(user);
        when(passwordHasher.matches(password, user.getPassword())).thenReturn(false);
        Result<User, AuthError> result = authenticateUserService.authenticate(email, password);
        assertEquals(AuthError.INVALID_CREDENTIALS, result.getError());
        assertTrue(result.isFailure());
        verify(loadUserByEmailPort).loadByEmail(email);
        verify(passwordHasher).matches(password, user.getPassword());


    }
}
