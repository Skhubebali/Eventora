package com.eventora.config;


import com.eventora.entity.UserEntity;
import com.eventora.repository.UserRepo;
import com.eventora.security.JwtService;
import com.eventora.service.UserDetailServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import java.util.Collections;
import java.util.Optional;

@Component

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserDetailServiceImpl userDetailService;


    public JwtAuthenticationFilter(JwtService jwtService, UserDetailServiceImpl userDetailService) {
        this.jwtService = jwtService;

        this.userDetailService = userDetailService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        try {
            if (header != null && header.startsWith("Bearer ")) {
                String token = header.substring(7);
                String subject = jwtService.extractSubject(token);
                UserDetails details = userDetailService.loadUserByUsername(subject);
                if (details.isEnabled() && details.isAccountNonExpired()
                        && details.isAccountNonLocked() && details.isCredentialsNonExpired()) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    details,
                                    null,
                                    details.getAuthorities()
                            );

                    SecurityContext auth = SecurityContextHolder.getContext();

                    if (auth.getAuthentication() == null) {
                        auth.setAuthentication(authToken);
                    }
                } else {
                    SecurityContextHolder.clearContext();
                    response.setStatus(401);
                    return;
                }
            } else if (header != null && !header.startsWith("Bearer ")) {
                SecurityContextHolder.clearContext();
                response.setStatus(401);
                return;
            }
        } catch (ExpiredJwtException e) {
            SecurityContextHolder.clearContext();
            response.setStatus(401);
            return;

        } catch (JwtException e) {
            SecurityContextHolder.clearContext();
            response.setStatus(401);
            return;
        } catch (UsernameNotFoundException e) {
            SecurityContextHolder.clearContext();
            response.setStatus(401);
            return;
        }
        filterChain.doFilter(request, response);

    }


}

