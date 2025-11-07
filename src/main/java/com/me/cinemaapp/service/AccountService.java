package com.me.cinemaapp.service;

import com.me.cinemaapp.converter.UserConverter;
import com.me.cinemaapp.dao.UserRepository;
import com.me.cinemaapp.entity.User;
import com.me.cinemaapp.error.exception.ResourceAlreadyExistException;
import com.me.cinemaapp.error.exception.ResourceNotFoundException;
import com.me.cinemaapp.model.request.UserSignInRequest;
import com.me.cinemaapp.model.request.UserSignUpRequest;
import com.me.cinemaapp.model.security.TokenDetail;
import com.me.cinemaapp.security.JWTProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTProvider jwtProvider;
    private final UserConverter userConverter;

    public void signUp(UserSignUpRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new ResourceAlreadyExistException("User already exists with this username.");
        }

        User user = userConverter.apply(request);
        userRepository.save(user);
    }

    public TokenDetail signIn(UserSignInRequest request) {
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with this username."));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new ResourceNotFoundException("Invalid username or password.");
        }
        return jwtProvider.generateToken(user.getId());
    }

}
