package com.app.app.application.service;


import com.app.app.domain.model.User;
import com.app.app.domain.port.in.LoadUserByEmailUseCase;
import com.app.app.domain.port.out.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoadUserByEmailService implements LoadUserByEmailUseCase {
    private final UserRepository userRepository;


    @Override
    public User loadByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
