package com.app.app.infraestructure.security.filter;


import com.app.app.domain.port.out.TokenService;
import com.app.app.infraestructure.security.excepcion.JwtAuthenticationException;
import com.app.app.infraestructure.security.excepcion.JwtErrorCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {



    private final TokenService tokenService;
    private final UserDetailsService userDetailsService;

    /** Patrones públicos (whitelist) */
    private final List<String> publicEndpointPatterns;

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return publicEndpointPatterns.stream()
                .anyMatch(pattern -> pathMatcher.match(pattern, path));
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        Optional<String> tokenOpt = extractToken(request);

        if (tokenOpt.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            tokenService.validateToken(tokenOpt.get());

            String email = tokenService.getSubject(tokenOpt.get());
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);

        } catch (JwtAuthenticationException ex) {
            SecurityContextHolder.clearContext();
            throw ex;

        } catch (Exception ex) {
            SecurityContextHolder.clearContext();
            throw new JwtAuthenticationException(JwtErrorCode.TOKEN_INVALID);
        }
    }

    /**
     * Extrae token del header "Authorization" o de cookie opcional
     */
    private Optional<String> extractToken(HttpServletRequest request) {
        // Prioriza header Authorization
        String bearer = request.getHeader("Authorization");
        if (bearer != null && bearer.startsWith("Bearer ")) {
            return Optional.of(bearer.substring(7));
        }

        // Alternativa: desde cookies
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            return Arrays.stream(cookies)
                    .filter(cookie -> "TOKEN".equals(cookie.getName())) // cambia por tu constante si quieres
                    .map(Cookie::getValue)
                    .findFirst();
        }

        return Optional.empty();
    }
}


