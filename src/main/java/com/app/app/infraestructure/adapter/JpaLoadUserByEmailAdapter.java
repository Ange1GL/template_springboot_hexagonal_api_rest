package com.app.app.infraestructure.adapter;


import com.app.app.domain.model.User;
import com.app.app.domain.port.out.LoadUserByEmailPort;
import com.app.app.domain.port.out.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JpaLoadUserByEmailAdapter implements LoadUserByEmailPort {
    private final UserRepository userRepository;

    @Override
    public User loadByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
    }
}
