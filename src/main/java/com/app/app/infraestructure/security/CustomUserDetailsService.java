package com.app.app.infraestructure.security;

import com.app.app.domain.model.User;
import com.app.app.domain.port.out.LoadUserByEmailPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final LoadUserByEmailPort loadUserByEmailPort;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        User user = loadUserByEmailPort.loadByEmail(email);

        return new CustomUserDetails(user);
    }
}
