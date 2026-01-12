package com.app.app.application;

import com.app.app.application.service.RegisterUserService;
import com.app.app.domain.model.User;
import com.app.app.domain.port.out.PasswordHasher;
import com.app.app.domain.port.out.UserRepository;
import com.app.app.domain.shared.AuthError;
import com.app.app.domain.shared.Result;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class RegisterUserUseCaseTest {
    @Mock
    UserRepository userRepository;

    @Mock
    PasswordHasher passwordHasher;

    @InjectMocks
    RegisterUserService registerUserService;

    @Test
    @DisplayName("Should register user successfully")
    void registerUser_success() {
        // given
        String email = "test@mail.com";
        String password = "1234";
        String hashed = "hashed123";

        when(userRepository.existsByEmail(email)).thenReturn(false);
        when(passwordHasher.hash(password)).thenReturn(hashed);
        when(userRepository.save(any()))
                .thenAnswer(inv -> inv.getArgument(0));

        // when
        Result<User, AuthError> result =
                registerUserService.register(email, password, "Test");

        // then
        assertTrue(result.isSuccess());
        assertEquals(email, result.getValue().getEmail());
        verify(userRepository).save(any());
    }

//    @Test
//    void registerUser_emailAlreadyExists() {
//        when(userRepository.existsByEmail(any()))
//                .thenReturn(true);
//
//        Result<User, AuthError> result =
//                registerUserService.register(
//                        "test@mail.com", "1234", "Test"
//                );
//
//        assertTrue(result.isFailure());
//        assertEquals(AuthError.EMAIL_ALREADY_IN_USE, result.getError());
//        verify(userRepository, never()).save(any());
//    }
}
