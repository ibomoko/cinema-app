package com.me.cinemaapp.converter;

import com.me.cinemaapp.entity.User;
import com.me.cinemaapp.enums.Role;
import com.me.cinemaapp.model.request.UserSignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class UserConverter implements Function<UserSignUpRequest, User> {

    private final PasswordEncoder passwordEncoder;

    @Override
    public User apply(UserSignUpRequest request) {
        return User.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .name(request.name())
                .surname(request.surname())
                .patronymic(request.patronymic())
                .role(Role.USER)
                .balance(BigDecimal.valueOf(100))
                .build();
    }
}
