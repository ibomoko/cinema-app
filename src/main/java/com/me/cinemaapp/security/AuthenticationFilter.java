package com.me.cinemaapp.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.me.cinemaapp.error.ErrorResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;
    private final JWTProvider jwtProvider;
    private final CustomUserDetailsService customUserDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (StringUtils.isNotBlank(authHeader)) {
            if (authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                if (StringUtils.isBlank(token)) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Bearer token in Authorization Header");
                    return;
                }
                    try {
                        String userId = jwtProvider.getUserIdByToken(token);
                        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userId);
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(userId , userDetails.getPassword(), userDetails.getAuthorities());
                        if (SecurityContextHolder.getContext().getAuthentication() == null) {
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        }
                    } catch (JWTVerificationException exc) {
                        var responseEntity = new ResponseEntity<>(new ErrorResponse(HttpStatus.UNAUTHORIZED, "Invalid Authorization header"), HttpStatus.UNAUTHORIZED);
                        response.setStatus(401);
                        response.getWriter().write(objectMapper.writeValueAsString(responseEntity.getBody()));
                        return;
                    }
            }
        }

        filterChain.doFilter(request, response);
    }
}
